package com.example.article_site.controller;

import com.example.article_site.domain.Author;
import com.example.article_site.domain.Question;
import com.example.article_site.dto.QuestionDetailDto;
import com.example.article_site.dto.QuestionListDto;
import com.example.article_site.form.AnswerForm;
import com.example.article_site.form.QuestionForm;
import com.example.article_site.service.AuthorService;
import com.example.article_site.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AuthorService authorService;

    @GetMapping("/list")
    public String list(Model model,
                        @RequestParam(value="page", defaultValue="0") int page) {
        Page<QuestionListDto> paging = questionService.getQuestionPageDtos(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model,
                         AnswerForm answerForm) {
        QuestionDetailDto questionDetailDto =  questionService.getQuestionDetailDto(id);
        model.addAttribute("question", questionDetailDto);
        return "question_detail";
    }

    @PreAuthorize(" isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize(" isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 Principal principal) {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        questionService.create(questionForm.getSubject(), questionForm.getContent(), principal.getName());
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm,
                                 @PathVariable("id") Long id,
                                 Principal principal) {
        Question question = questionService.getQuestionById(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Long id,
                                 Principal principal) {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = questionService.getQuestionById(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/{id}" ;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal,
                                 @PathVariable("id") Long id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteQuestion(Principal principal,
                               @PathVariable("id") Long id){
        Question question = questionService.getQuestion(id);
        Author author = authorService.findByUsername(principal.getName());
        questionService.vote(question, author);
        log.debug("voter size : {}", question.getVoter().size());
        return "redirect:/question/detail/{id}";
    }
}
