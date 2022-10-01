package be.digitalcity.projetspringrest.repositories;


import be.digitalcity.projetspringrest.models.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByName(String role);
}
