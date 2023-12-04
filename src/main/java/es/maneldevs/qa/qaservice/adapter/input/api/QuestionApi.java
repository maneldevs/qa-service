package es.maneldevs.qa.qaservice.adapter.input.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.filter.QuestionFilter;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import jakarta.validation.Valid;

@RequestMapping("questions")
public interface QuestionApi {
    
    @GetMapping
    List<QuestionResponse> index(@Valid QuestionFilter filter);

    @GetMapping("{code}")
    QuestionDetailResponse show(@PathVariable String code);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    QuestionResponse create(@Valid @RequestBody QuestionCommand command);

    @PostMapping("{questionCode}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    QuestionDetailResponse createAnswer(@PathVariable String questionCode, @Valid @RequestBody AnswerCommand command);

    @DeleteMapping("{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String code);

}
