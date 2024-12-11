package com.example.article_site.dto;

import com.example.article_site.domain.Answer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDto {

    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String author;
    private Integer likes;

    public static AnswerDto createAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setContent(answer.getContent());
        answerDto.setCreateDate(answer.getCreateDate());
        answerDto.setModifyDate(answer.getModifyDate());
        answerDto.setAuthor(answer.getAuthor().getUsername());
        answerDto.setLikes(answer.getVoter().size());
        return answerDto;
    }
}
