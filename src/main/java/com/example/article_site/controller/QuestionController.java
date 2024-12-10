package com.example.article_site.controller;

import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.dto.QuestionListDto;
import com.example.article_site.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,
                         Model model) {
        Optional<QuestionDetailDto> dtoOpt =  questionService.getQuestionDetailDtoOpt(id);

        return "question_detail";
    }
}
