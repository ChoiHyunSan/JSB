package com.example.article_site.controller;

import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionListDto;
import com.example.article_site.repository.QuestionRepository;
import com.example.article_site.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<QuestionListDto> list = questionService.getQuestionListDtos();
        model.addAttribute("questionList", list);
        return "question_list";
    }
}
