package es.maneldevs.qa.qaservice.application.in;

import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import jakarta.validation.Valid;

public interface QuestionCommandUseCase {
    QuestionResponse createQuestion(@Valid QuestionCommand command);
    QuestionDetailResponse createAnswer(String questionCode, @Valid AnswerCommand command);
    void deleteQuestion(String questionCode);
}
