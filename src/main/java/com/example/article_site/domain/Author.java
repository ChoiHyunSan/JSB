package com.example.article_site.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Author {
    @Id @GeneratedValue
    @Column(name = "author_id")
    private long id;

    private String username;
    private String email;
    private String password;
}
