package com.example.article_site.controller;

import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.form.AnswerForm;
import com.example.article_site.service.AnswerService;
import com.example.article_site.service.AuthorService;
import com.example.article_site.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AuthorService authorService;

    @PreAuthorize(" isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(Model model,
                         @PathVariable("id") Long id,
                         @Valid AnswerForm answerForm,
                         BindingResult bindingResult,
                         Principal principal) {
        if(bindingResult.hasErrors()) {
            QuestionDetailDto questionDetailDto = questionService.getQuestionDetailDto(id);
            model.addAttribute("question", questionDetailDto);
            return "question_detail";
        }

        Question question = questionService.getQuestionById(id);
        Author author = authorService.findByUsername(principal.getName());
        answerService.Create(question, answerForm.getContent(), author);
        return "redirect:/question/detail/{id}";
    }
}
