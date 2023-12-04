package es.maneldevs.qa.qaservice.adapter.input.api.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import es.maneldevs.qa.qaservice.adapter.input.api.QuestionApi;
import es.maneldevs.qa.qaservice.application.in.QuestionCommandUseCase;
import es.maneldevs.qa.qaservice.application.in.QuestionQueryUseCase;
import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.filter.QuestionFilter;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionApi {
    private final QuestionQueryUseCase questionQueryUseCase;
    private final QuestionCommandUseCase questionCommandUseCase;

    @Override
    public List<QuestionResponse> index(QuestionFilter filter) {
        return questionQueryUseCase.getQuestionsByUsername(filter.getUsername());
    }

    @Override
    public QuestionDetailResponse show(String code) {
        return questionQueryUseCase.getQuestion(code);
    }

    @Override
    public QuestionResponse create(@Valid QuestionCommand command) {
        QuestionResponse res = questionCommandUseCase.createQuestion(command);
        return res;
    }

    @Override
    public QuestionDetailResponse createAnswer(String questionCode, @Valid AnswerCommand command) {
        return questionCommandUseCase.createAnswer(questionCode, command);
    }

    @Override
    public void delete(String code) {
        questionCommandUseCase.deleteQuestion(code);
    }

}
