package com.example.article_site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Answer {

    @Id @GeneratedValue
    @Column(name = "answer_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;
}
