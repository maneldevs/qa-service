package es.maneldevs.qa.qaservice.application.model.filter;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionFilter {
    @NotBlank
    private String username;
}
