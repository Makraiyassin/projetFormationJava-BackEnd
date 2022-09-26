package be.digitalcity.projetspringrest.repositories;


import be.digitalcity.projetspringrest.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findDistinctByStreetAndNumberAndCp(String street, Integer number, Integer cp);
}
