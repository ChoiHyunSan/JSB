package com.example.article_site.controller;

import com.example.article_site.domain.Answer;
import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.repository.QuestionRepository;
import com.example.article_site.service.AnswerService;
import com.example.article_site.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String create(Model model,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "content") String content) {
        Question question = questionService.getQuestionById(id);
        answerService.Create(question, content);
        return "redirect:/question/detail/{id}";
    }
}
