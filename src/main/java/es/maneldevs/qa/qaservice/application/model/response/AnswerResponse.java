package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class AnswerResponse {
    private final String code;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final Boolean selected;
}
