package com.example.article_site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<Answer>();

    @ManyToMany
    Set<Author> voter = new HashSet<Author>();

    protected Question() {}

    public static Question createQuestion(String subject, String content, Author author) {
        Question question = new Question();
        question.subject = subject;
        question.content = content;
        question.author = author;
        question.createDate = LocalDateTime.now();
        question.modifyDate = null;
        return question;
    }

    public static void modifyQuestion(Question question, String subject, String content) {
        question.subject = subject;
        question.content = content;
        question.modifyDate = LocalDateTime.now();
    }
}
