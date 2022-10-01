package be.digitalcity.projetspringrest.repositories;


import be.digitalcity.projetspringrest.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findDistinctByStreetAndNumberAndCp(String street, Integer number, Integer cp);
}
