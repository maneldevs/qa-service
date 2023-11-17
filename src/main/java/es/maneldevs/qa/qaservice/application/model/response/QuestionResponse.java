package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class QuestionResponse {
    private final String code;
    private final String username;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<String> tags;
}
