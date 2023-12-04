package es.maneldevs.qa.qaservice.application.model.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class AnswerCommand {
    @NotBlank
    private final String username;
    @NotBlank
    private final String content;
}
