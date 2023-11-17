package es.maneldevs.qa.qaservice.adapter.input.api.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.adapter.input.api.QuestionApi;
import es.maneldevs.qa.qaservice.application.model.filter.QuestionFilter;
import es.maneldevs.qa.qaservice.application.model.response.AnswerResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;

@RestController
public class QuestionController implements QuestionApi {

    @Override
    public List<QuestionResponse> index(QuestionFilter filter) {
        if (!"maneldevs".equals(filter.getUsername())) {
            return List.of();
        }
        QuestionResponse question = QuestionResponse.builder()
                .code(UUID.randomUUID().toString())
                .title("this is my question")
                .content("this is the question content")
                .username(filter.getUsername())
                .createdAt(LocalDateTime.now())
                .tags(List.of("tag1", "tag2")).build();
        return List.of(question);
    }

    @Override
    public QuestionDetailResponse show(String code) {
        if (!"281a6c13-56b5-4c12-84ce-f12bfa3e1bc3".equals(code)) {
            throw new NotFoundException("question not found");
        }
        AnswerResponse answer1 = AnswerResponse.builder()
                .code(UUID.randomUUID().toString())
                .content("this is the answer1 content")
                .username("user1")
                .selected(false)
                .createdAt(LocalDateTime.now())
                .build();
        AnswerResponse answer2 = AnswerResponse.builder()
                .code(UUID.randomUUID().toString())
                .content("this is the answer2 content")
                .username("user1")
                .selected(false)
                .createdAt(LocalDateTime.now())
                .build();
        return QuestionDetailResponse.detailBuilder()
                .code("281a6c13-56b5-4c12-84ce-f12bfa3e1bc3")
                .title("this is my question")
                .content("this is the question content")
                .username("maneldevs")
                .createdAt(LocalDateTime.now())
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answer1, answer2)).build();
    }

}
