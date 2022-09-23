package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.OmnithequeMapper;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.models.dtos.ProductDto;
import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;

@Service
public class OmnithequeService {
    private final OmnithequeMapper mapper;
    private final OmnithequeRepository repository;

    public OmnithequeService(OmnithequeMapper mapper, OmnithequeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public OmnithequeDto getOne(Long id){
        Omnitheque omnitheque = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Aucune omnitheque trouvé avec l'id {"+id+"}"));
        return mapper.entityToDto(omnitheque);
    }
    public List<OmnithequeDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

}
