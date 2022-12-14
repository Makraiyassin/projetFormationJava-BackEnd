package be.digitalcity.projetspringrest.repositories;

import be.digitalcity.projetspringrest.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByNameContainingOrDescriptionContainingIgnoreCase(String name, String description);

}
