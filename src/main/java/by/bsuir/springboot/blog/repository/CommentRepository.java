package by.bsuir.springboot.blog.repository;

import by.bsuir.springboot.blog.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository  extends CrudRepository<Comment, Long> {
}
