package be.digitalcity.projetspringrest.repositories;

import be.digitalcity.projetspringrest.models.entities.Borrow;
import be.digitalcity.projetspringrest.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BorrowRepository extends JpaRepository<Borrow,Long> {
    long countAllByProductListContainsAndEndBorrowAfter(Product product, LocalDate date);
    //sert à avoir le compte d'emprunt en cours sur un produit. => si le compte est inferieur a la quantité : produit dispo
}
