package com.example.article_site.service;

import com.example.article_site.dto.QuestionListDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionService {

    List<QuestionListDto> getQuestionListDtos();
}
