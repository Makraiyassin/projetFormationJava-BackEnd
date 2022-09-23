package be.digitalcity.projetspringrest.repositories;


import be.digitalcity.projetspringrest.models.entities.Omnitheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OmnithequeRepository extends JpaRepository<Omnitheque,Long> {

}
