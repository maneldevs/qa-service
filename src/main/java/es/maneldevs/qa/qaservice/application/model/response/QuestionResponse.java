package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;
import java.util.List;

import es.maneldevs.qa.qaservice.domain.Question;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class QuestionResponse {
    private final String code;
    private final String username;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<String> tags;

    public QuestionResponse(Question question) {
        this.code = question.getCode();
        this.username = question.getUsername();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createdAt = question.getCreatedAt();
        this.tags = question.getTags();
    }
}
