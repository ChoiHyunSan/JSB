package com.example.article_site.service;

import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionListDto;
import com.example.article_site.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.article_site.dto.QuestionListDto.createQuestionListDto;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * question 들을 QuestionListDto 정보로 변환하여 넘겨준다.
     */
    @Override
    public List<QuestionListDto> getQuestionListDtos() {
        return questionRepository.findAll().stream()
                .map(QuestionListDto::createQuestionListDto)
                .toList();
    }
}
