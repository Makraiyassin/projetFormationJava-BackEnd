package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.AddressMapper;
import be.digitalcity.projetspringrest.mappers.OmnithequeMapper;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.entities.Address;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.models.entities.Product;
import be.digitalcity.projetspringrest.models.forms.OmnithequeForm;
import be.digitalcity.projetspringrest.repositories.AddressRepository;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import be.digitalcity.projetspringrest.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OmnithequeService {
    private final OmnithequeMapper mapper;
    private final OmnithequeRepository repository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final UsersDetailsServiceImpl usersService;
    private final ProductRepository productRepository;

    public OmnithequeService(OmnithequeMapper mapper, OmnithequeRepository repository, AddressRepository addressRepository, AddressMapper addressMapper, AddressService addressService, UsersDetailsServiceImpl usersService, ProductRepository productRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
        this.usersService = usersService;
        this.productRepository = productRepository;
    }

    public OmnithequeDto getOne(Long id){
        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(omnitheque);
    }
    public List<OmnithequeDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public OmnithequeDto create(OmnithequeForm form, Authentication auth){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");
        Omnitheque omnitheque = mapper.formToEntity( form );
        Address addressChecked = addressService.search(form.getAddress());
        if(addressChecked != null ) {
            omnitheque.setAddress(addressChecked);
        }else {
            omnitheque.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
        }

        return mapper.entityToDto(usersService.addOmnitheque(auth,omnitheque));

    }
    public OmnithequeDto update( Authentication auth, OmnithequeForm form){
        if( form == null) throw new IllegalArgumentException("le formulaire ne peut pas être null");

        Omnitheque toUpdate = repository.findById(form.getId()).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+form.getId()+"}"));

        if(form.getName() != null) toUpdate.setName(form.getName());
        if(form.getEmail() != null) toUpdate.setEmail(form.getEmail());
        if(form.getPhone() != null) toUpdate.setPhone(form.getPhone());

        if(form.getAddress() != null) {
            Address addressChecked = addressService.search(form.getAddress());
            if(addressChecked != null ) {
                toUpdate.setAddress(addressChecked);
            }else {
                toUpdate.setAddress(addressRepository.save(addressMapper.formToEntity(form.getAddress())));
            }
        }
        return mapper.entityToDto(repository.save(toUpdate));
    }

    public OmnithequeDto delete(Long id){
        if(id == null)
            throw new IllegalArgumentException("l'id ne peut pas être null");

        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        repository.delete(omnitheque);
        return mapper.entityToDto(omnitheque);
    }


    public Product addProduct(Authentication auth, long omnithequeId, Product product){
        if( usersService.getUser(auth.getName()).getOmnitheque().getId() != omnithequeId) throw new RuntimeException("blabla") ;

        Omnitheque omnitheque = repository.findById(omnithequeId).get();

        if(omnitheque.getProductList().stream().anyMatch(p ->{
            return (
                    p.getName().equals(product.getName()) &&
                    p.getCategory().equals(product.getCategory())
            );
        })){
            Product productToUpdate = omnitheque.getProductList().stream().filter(p ->{
                return (
                        p.getName().equals(product.getName()) &&
                        p.getCategory().equals(product.getCategory())
                );
            }).toList().get(0);
            if(product.getQuantity() != 0) productToUpdate.setQuantity(product.getQuantity());
            if(product.getImage() != null) productToUpdate.setImage(product.getImage());
            if(product.getDescription() != null) productToUpdate.setDescription(product.getDescription());
            return productRepository.save(productToUpdate);
        }
        omnitheque.addProduct(productRepository.save(product));
        repository.save(omnitheque);
        return product;
    }
}
