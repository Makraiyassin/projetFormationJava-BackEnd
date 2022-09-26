package be.digitalcity.projetspringrest.repositories;


import be.digitalcity.projetspringrest.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

//    TODO : Verif si addresse existe

}
