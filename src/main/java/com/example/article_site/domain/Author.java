package com.example.article_site.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private long id;

    @Column(unique = true)
    private String username;

    private String email;

    @Column(unique = true)
    private String password;

    protected Author() {}

    public static Author createAuthor(String username, String email, String password) {
        Author author = new Author();
        author.username = username;
        author.email = email;
        author.password = password;
        return author;
    }

    public void modifyPassword(String newPassword) {
        this.password = newPassword;
    }
}
