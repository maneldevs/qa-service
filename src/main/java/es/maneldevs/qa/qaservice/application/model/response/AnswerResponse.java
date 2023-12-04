package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;

import es.maneldevs.qa.qaservice.domain.Answer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class AnswerResponse {
    private final String code;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final Boolean selected;

    public AnswerResponse(Answer answer) {
        this.code = answer.getCode();
        this.username = answer.getUsername();
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
        this.selected = answer.getSelected();
    }
}
