package be.digitalcity.projetspringrest.repositories;

import be.digitalcity.projetspringrest.models.entities.Post;
import be.digitalcity.projetspringrest.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByTitleContainingOrContentContaining(String title, String content);

}
