package com.example.article_site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Author author;

    protected Answer() {}

    public static Answer createAnswer(Question question, String content, Author author) {
        Answer answer = new Answer();
        answer.question = question;
        answer.content = content;
        answer.author = author;
        answer.createDate = LocalDateTime.now();

        question.getAnswerList().add(answer);
        return answer;
    }
}
