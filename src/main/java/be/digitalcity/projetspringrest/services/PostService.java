package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.exceptions.UnauthorizedException;
import be.digitalcity.projetspringrest.mappers.PostMapper;
import be.digitalcity.projetspringrest.models.dtos.PostDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Post;
import be.digitalcity.projetspringrest.models.entities.Users;
import be.digitalcity.projetspringrest.models.forms.PostForm;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import be.digitalcity.projetspringrest.repositories.PostRepository;
import be.digitalcity.projetspringrest.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostMapper mapper;
    private final PostRepository repository;
    private final OmnithequeRepository omnithequeRepository;
    private final UsersRepository usersRepository;

    public PostService(PostMapper mapper, PostRepository repository, OmnithequeRepository omnithequeRepository, UsersRepository usersRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.omnithequeRepository = omnithequeRepository;
        this.usersRepository = usersRepository;
    }

    public PostDto getOne(Long id){
        Post post = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune publication trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(post);
    }

    public List<PostDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    public PostDto create(Authentication auth, PostForm form){
        if( form == null ) throw new IllegalArgumentException("le formulaire ne peut pas être null");
        Long omnithequeId = usersRepository.findByEmail(auth.getName()).orElseThrow(()->
            new EntityNotFoundException("utilisateur"+auth.getName()+" n'existe pas")
        ).getOmnitheque().getId();

        form.setOmnithequeId(omnithequeId);
        Post post = mapper.formToEntity( form );

        Omnitheque omnitheque = omnithequeRepository.findById(omnithequeId).orElseThrow(
                ()->new EntityNotFoundException("aucune omnitheque trouvé avec l'id {"+form.getOmnithequeId()+"}")
        );

        post.setOmnitheque(omnitheque);
        post = updateIfPostExist(omnitheque,post);

        return  mapper.entityToDto(repository.save(post));
    }

    public PostDto update(Authentication auth, PostForm form){
        if(form == null )
            throw new IllegalArgumentException("le formulaire et l'id ne peuvent pas être null");

        Post toUpdate = repository.findById(form.getId()).orElseThrow(()->new EntityNotFoundException("Aucune publication trouvée avec l'id {"+form.getId()+"}"));

        Users user;
        Omnitheque omnitheque;
        if(usersRepository.findByEmail(auth.getName()).isPresent()){
            user = usersRepository.findByEmail(auth.getName()).get();
            if(user.getOmnitheque() != null) omnitheque = user.getOmnitheque();
            else throw new EntityNotFoundException("l'utilisateur avec l'id {"+user.getId()+"} ne possede pas d'omnitheque");
        } else  throw new UsernameNotFoundException("Une erreur est survenu lors de la connexion. veuillez verifier votre identifiant");

        if(omnitheque.getPostList().stream().anyMatch(p->p.getId().equals(form.getId()))){
            if(form.getTitle() != null) toUpdate.setTitle(form.getTitle());
            if(form.getContent() != null) toUpdate.setContent(form.getContent());
            if(form.getDate() != null) toUpdate.setDate(form.getDate());
            if(form.getImage() != null) toUpdate.setImage(form.getImage());
            toUpdate = repository.save(toUpdate);
        } else throw new UnauthorizedException("la publication avec l'id {"+form.getId()+"} n'appartient pas à  l'omnitheque avec l'id {"+omnitheque.getId()+"}");

        PostDto postDto = mapper.entityToDto(toUpdate);
        postDto.setOmnithequeId((omnitheque.getId()));
        return postDto;
    }

    public PostDto delete(Long id, Authentication auth){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        if(usersRepository.findByEmail(auth.getName()).isPresent()){
            Users user = usersRepository.findByEmail(auth.getName()).get();
            if(user.getOmnitheque() != null) {
                Omnitheque omnitheque = user.getOmnitheque();
                if(omnitheque.getPostList().stream().anyMatch(p -> p.getId().equals(id))){
                    Post post = omnitheque.getPostList().stream().findFirst().orElseThrow(
                            ()->new EntityNotFoundException("Aucun produit trouvé avec l'id {"+id+"}")
                    );
                    repository.delete(post);
                    post.setId(null);
                    return mapper.entityToDto(post);
                }else throw new EntityNotFoundException("l'omnitheque avec l'id {"+omnitheque.getId()+"} ne possede le post avec l'id {"+id+"}");
            }
            else throw new EntityNotFoundException("l'utilisateur avec l'id {"+user.getId()+"} ne possede pas d'omnitheque");
        } else  throw new UsernameNotFoundException("Une erreur est survenu lors de la connexion. veuillez verifier votre identifiant");
    }

    public List<PostDto> search(String word) {
        return repository.findAllByTitleContainingOrContentContaining(word,word).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    public Post updateIfPostExist(Omnitheque omnitheque, Post post) {
        if(omnitheque.getPostList().stream().anyMatch(p ->{
            return (
                    p.getTitle().equals(post.getTitle()) &&
                    p.getContent().equals(post.getContent())
            );
        })){
            Post postToUpdate = omnitheque.getPostList().stream().filter(p ->{
                return (
                        p.getTitle().equals(post.getTitle()) &&
                        p.getContent().equals(post.getTitle())
                );
            }).collect(Collectors.toList()).get(0);
            if(post.getImage() != null) postToUpdate.setImage(post.getImage());
            if(post.getDate() != null) postToUpdate.setDate(post.getDate());
            return postToUpdate;
        }
        return post;
    }
}


