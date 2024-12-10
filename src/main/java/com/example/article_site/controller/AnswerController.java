package com.example.article_site.controller;

import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.form.AnswerForm;
import com.example.article_site.service.AnswerService;
import com.example.article_site.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String create(Model model,
                         @PathVariable("id") Long id,
                         @Valid AnswerForm answerForm,
                         BindingResult bindingResult) {
        QuestionDetailDto questionDetailDto = questionService.getQuestionDetailDto(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", questionDetailDto);
            return "question_detail";
        }

        Question question = questionService.getQuestionById(id);
        answerService.Create(question, answerForm.getContent());
        return "redirect:/question/detail/{id}";
    }
}
