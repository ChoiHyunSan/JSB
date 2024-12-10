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

    public void Create(Question question, String content) {
        // TODO : 질문자의 id를 이용하여 Author를 넣어주어야 한다.
        //        일단은 질문자와 작성자가 같다는 느낌으로 진행

        answerRepository.save(Answer.createAnswer(question, content, question.getAuthor()));
    }
}
