package by.bsuir.springboot.blog.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String user_name, full_text_comment;


    public Comment() {
    }

    public Comment(String user_name, String full_text_comment) {
        this.user_name = user_name;
        this.full_text_comment = full_text_comment;
    }

    public Long getId() {
        return id;
    }

    public String getUser_name() {
        return user_name != null ? user_name : "<none>";
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFull_text_comment() {
        return full_text_comment;
    }

    public void setFull_text_comment(String full_text_comment) {
        this.full_text_comment = full_text_comment;
    }
}
