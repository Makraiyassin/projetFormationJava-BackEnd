package be.digitalcity.projetspringrest.mappers;

import be.digitalcity.projetspringrest.models.dtos.PostDto;
import be.digitalcity.projetspringrest.models.entities.Post;
import be.digitalcity.projetspringrest.models.forms.PostForm;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {

    public PostDto entityToDto(Post entity){
        PostDto dto = new PostDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        if(entity.getOmnitheque() != null) dto.setOmnithequeId(entity.getOmnitheque().getId());

        return dto;
    }
    public Post formToEntity(PostForm form){
        Post entity = new Post();
        entity.setTitle(form.getTitle());
        entity.setContent(form.getContent());
        entity.setImage(form.getImage());
        entity.setDate(form.getDate());
        return entity;
    }

}
