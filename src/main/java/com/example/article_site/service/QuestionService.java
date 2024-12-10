package com.example.article_site.service;

import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.dto.QuestionListDto;
import com.example.article_site.exception.DataNotFoundException;
import com.example.article_site.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.article_site.dto.QuestionDetailDto.createQuestionDetailDto;
import static com.example.article_site.exception.Message.QUESTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * question 들을 QuestionListDto 정보로 변환하여 넘겨준다.
     */
    public List<QuestionListDto> getQuestionListDtos() {
        return questionRepository.findAll().stream()
                .map(QuestionListDto::createQuestionListDto)
                .toList();
    }

    /**
     * 상세 페이지로 보낼 id에 맞는 질문 정보를 취합하여 반환
     * 조회되지 않는 질문의 경우 DataNotFoundException 을 던진다.
     */
    public QuestionDetailDto getQuestionDetailDtoOpt(Long id) {
        Optional<Question> questionOpt = questionRepository.findById(id);
        if(questionOpt.isEmpty()){
            throw new DataNotFoundException(QUESTION_NOT_FOUND);
        }
        return createQuestionDetailDto(questionOpt.get());
    }

    /**
     *  id 를 통해 질문을 조회하여 반환
     */
    public Question getQuestionById(Long id) {
        Optional<Question> byId = questionRepository.findById(id);
        if(byId.isEmpty()){
            throw new DataNotFoundException(QUESTION_NOT_FOUND);
        }
        return byId.get();
    }
}
