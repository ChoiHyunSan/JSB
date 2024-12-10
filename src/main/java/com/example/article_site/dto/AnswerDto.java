package com.example.article_site.dto;

import com.example.article_site.domain.Answer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDto {

    private String content;
    private LocalDateTime createDate;
    private String author;

    public static AnswerDto createAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setContent(answer.getContent());
        answerDto.setCreateDate(answer.getCreateDate());
        answerDto.setAuthor(answer.getAuthor().getUsername());
        return answerDto;
    }
}
