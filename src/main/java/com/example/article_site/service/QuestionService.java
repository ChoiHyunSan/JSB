package com.example.article_site.service;

import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.dto.QuestionListDto;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<QuestionListDto> getQuestionListDtos();
    Optional<QuestionDetailDto> getQuestionDetailDtoOpt(Long id);
}
