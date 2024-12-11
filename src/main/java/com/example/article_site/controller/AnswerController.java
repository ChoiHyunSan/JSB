package com.example.article_site.controller;

import com.example.article_site.domain.Answer;
import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.form.AnswerForm;
import com.example.article_site.service.AnswerService;
import com.example.article_site.service.AuthorService;
import com.example.article_site.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
        Answer answer = answerService.create(question, answerForm.getContent(), author);
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm,
                               @PathVariable("id") Long id,
                               Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm,
                               BindingResult bindingResult,
                               @PathVariable("id") Long id,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal,
                               @PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteAnswer(Principal principal,
                             @PathVariable("id") Long id){
        Answer answer = answerService.getAnswer(id);
        Author author = authorService.findByUsername(principal.getName());
        answerService.vote(answer, author);
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(), answer.getId());
    }
}
