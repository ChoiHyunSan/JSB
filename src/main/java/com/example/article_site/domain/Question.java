package com.example.article_site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Question {
    @Id @GeneratedValue
    @Column(name = "question_id")
    private long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<Answer>();
}
