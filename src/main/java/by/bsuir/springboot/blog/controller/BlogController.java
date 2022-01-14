package by.bsuir.springboot.blog.controller;

import by.bsuir.springboot.blog.model.Comment;
import by.bsuir.springboot.blog.model.Post;
import by.bsuir.springboot.blog.repository.CommentRepository;
import by.bsuir.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Controller
@Transactional
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){ return "redirect:/blog"; }

        Post post = postRepository.findById(id).orElseThrow();
        //Iterable<Comment> comments = commentRepository.findAll();
        Set<Comment> comments=post.getComments();
        post.setViews(post.getViews()+1);
        postRepository.save(post);
        //ArrayList<Post> res = new ArrayList<>();
        //post.ifPresent(res::add);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){ return "redirect:/blog"; }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/add_comment")
    public String blogAddComment(@PathVariable(value = "id") long id, @RequestParam String user_name, @RequestParam String full_text_comment, Model model){
        Post post=postRepository.findById(id).orElseThrow();
        Comment comment = new Comment(user_name, full_text_comment);
        post.addComment(comment);
        postRepository.save(post);
        return "redirect:/blog/{id}";
    }

    @PostMapping("/blog/{id}/remove_comment/{elemid}")
    public String blogRemoveComment(@PathVariable(value = "id") long id, @PathVariable(value = "elemid") long elemid, Model model){
        Post post=postRepository.findById(id).orElseThrow();
        Comment comment = commentRepository.findById(elemid).orElseThrow();
        post.removeComment(comment);
        //commentRepository.deleteById(elemid);
        postRepository.save(post);
        return "redirect:/blog/{id}";
    }

}
