package com.example.article_site.service;

import com.example.article_site.domain.Answer;
import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void Create(Question question, String content, Author author) {
        answerRepository.save(Answer.createAnswer(question, content, author));
    }
}
