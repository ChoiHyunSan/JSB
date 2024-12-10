package com.example.article_site.dto;

import com.example.article_site.domain.Question;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDetailDto {

    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private String author;
    private List<AnswerDto> answerList;

    public static QuestionDetailDto createQuestionDetailDto(Question question) {
        QuestionDetailDto dto = new QuestionDetailDto();
        dto.setId(question.getId());
        dto.setSubject(question.getSubject());
        dto.setContent(question.getContent());
        dto.setCreateDate(question.getCreateDate());
        dto.setAuthor(question.getAuthor().getUsername());
        dto.setAnswerList(question.getAnswerList().stream()
                .map(AnswerDto::createAnswerDto)
                .toList());
        return dto;
    }
}
