package by.bsuir.springboot.blog.repository;

import by.bsuir.springboot.blog.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
