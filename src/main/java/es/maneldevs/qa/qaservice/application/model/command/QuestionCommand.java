package es.maneldevs.qa.qaservice.application.model.command;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class QuestionCommand {
    @NotBlank
    private final String username;
    @NotBlank
    private final String title;
    private final String content;
    private final List<String> tags;
}
