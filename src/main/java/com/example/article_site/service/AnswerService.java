package com.example.article_site.service;

import com.example.article_site.domain.Answer;
import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.exception.DataNotFoundException;
import com.example.article_site.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, Author author) {
        answerRepository.save(Answer.createAnswer(question, content, author));
    }

    public void modify(Answer answer, String content) {
        Answer.modifyAnswer(answer, content);
        answerRepository.save(answer);
    }

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if(answer.isEmpty()){
            throw new DataNotFoundException("Answer Not Found");
        }
        return answer.get();
    }

    public void delete(Answer answer){
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, Author author) {
        answer.getVoter().add(author);
        answerRepository.save(answer);
    }
}
