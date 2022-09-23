package be.digitalcity.projetspringrest.services;

import be.digitalcity.projetspringrest.mappers.OmnithequeMapper;
import be.digitalcity.projetspringrest.models.dtos.OmnithequeDto;
import be.digitalcity.projetspringrest.repositories.OmnithequeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmnithequeService {
    private final OmnithequeMapper mapper;
    private final OmnithequeRepository repository;

    public OmnithequeService(OmnithequeMapper mapper, OmnithequeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<OmnithequeDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

}
