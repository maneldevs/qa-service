package es.maneldevs.qa.qaservice.adapter.input.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
