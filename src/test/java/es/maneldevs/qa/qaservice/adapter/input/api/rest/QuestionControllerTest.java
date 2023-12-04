package es.maneldevs.qa.qaservice.adapter.input.api.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.application.in.QuestionCommandUseCase;
import es.maneldevs.qa.qaservice.application.in.QuestionQueryUseCase;
import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.response.AnswerResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = QuestionController.class)
public class QuestionControllerTest {
    private static final String EXISTENT_USERNAME = "username";
    private List<QuestionResponse> questionResponses;
    private QuestionDetailResponse questionDetailResponse1;
    @MockBean
    private QuestionQueryUseCase questionQueryUseCase;
    @MockBean
    private QuestionCommandUseCase questionCommandUseCase;
    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void beforeEach() {
        AnswerResponse answerResponse1 = AnswerResponse.builder()
                .code("acode1")
                .username("ausername1")
                .content("acontent1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1, 1))
                .selected(false)
                .build();
        QuestionResponse questionResponse1 = QuestionResponse.builder()
                .code("code1")
                .username(EXISTENT_USERNAME)
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1, 1))
                .tags(List.of("tag1", "tag2"))
                .build();
        QuestionResponse questionResponse2 = QuestionResponse.builder()
                .code("code2")
                .username(EXISTENT_USERNAME)
                .title("title2")
                .content("content2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1, 1))
                .tags(List.of("tag1", "tag2"))
                .build();
        questionDetailResponse1 = QuestionDetailResponse.detailFromQuestionBuilder()
                .code("code1")
                .username(EXISTENT_USERNAME)
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1, 1))
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answerResponse1))
                .build();
        questionResponses = List.of(questionResponse1, questionResponse2);
    }

    @Test
    void index_existentUsername_questionResponseList() {
        QuestionResponse questionResponse1 = questionResponses.get(0);
        QuestionResponse questionResponse2 = questionResponses.get(1);
        when(questionQueryUseCase.getQuestionsByUsername(EXISTENT_USERNAME)).thenReturn(questionResponses);
        webClient
                .get()
                .uri("/questions?username=" + EXISTENT_USERNAME)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$.[0].username").isEqualTo(EXISTENT_USERNAME)
                .jsonPath("$.[0].code").isEqualTo(questionResponse1.getCode())
                .jsonPath("$.[0].title").isEqualTo(questionResponse1.getTitle())
                .jsonPath("$.[0].content").isEqualTo(questionResponse1.getContent())
                .jsonPath("$.[0].createdAt").isEqualTo(questionResponse1.getCreatedAt().toString())
                .jsonPath("$.[0].tags.[0]").isEqualTo(questionResponse1.getTags().get(0))
                .jsonPath("$.[0].tags.[1]").isEqualTo(questionResponse1.getTags().get(1))
                .jsonPath("$.[1].username").isEqualTo(EXISTENT_USERNAME)
                .jsonPath("$.[1].code").isEqualTo(questionResponse2.getCode())
                .jsonPath("$.[1].title").isEqualTo(questionResponse2.getTitle())
                .jsonPath("$.[1].content").isEqualTo(questionResponse2.getContent())
                .jsonPath("$.[1].createdAt").isEqualTo(questionResponse2.getCreatedAt().toString())
                .jsonPath("$.[1].tags.[0]").isEqualTo(questionResponse2.getTags().get(0))
                .jsonPath("$.[1].tags.[1]").isEqualTo(questionResponse2.getTags().get(1));
    }

    @Test
    void index_blankUsername_422() {
        webClient
                .get()
                .uri("/questions")
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Validation failed")
                .jsonPath("$.errors.username").isEqualTo("must not be blank");
    }

    @Test
    void show_existentCode_questionDetailResponse() {
        when(questionQueryUseCase.getQuestion("code1")).thenReturn(questionDetailResponse1);
        webClient
                .get()
                .uri("/questions/code1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo(EXISTENT_USERNAME)
                .jsonPath("$.code").isEqualTo(questionDetailResponse1.getCode())
                .jsonPath("$.title").isEqualTo(questionDetailResponse1.getTitle())
                .jsonPath("$.content").isEqualTo(questionDetailResponse1.getContent())
                .jsonPath("$.createdAt").isEqualTo(questionDetailResponse1.getCreatedAt().toString())
                .jsonPath("$.tags.[0]").isEqualTo(questionDetailResponse1.getTags().get(0))
                .jsonPath("$.tags.[1]").isEqualTo(questionDetailResponse1.getTags().get(1))
                .jsonPath("$.answers.[0].code").isEqualTo(questionDetailResponse1.getAnswers().get(0).getCode())
                .jsonPath("$.answers.[0].username").isEqualTo(questionDetailResponse1.getAnswers().get(0).getUsername())
                .jsonPath("$.answers.[0].content").isEqualTo(questionDetailResponse1.getAnswers().get(0).getContent())
                .jsonPath("$.answers.[0].createdAt").isEqualTo(questionDetailResponse1.getAnswers().get(0).getCreatedAt().toString())
                .jsonPath("$.answers.[0].selected").isEqualTo(questionDetailResponse1.getAnswers().get(0).getSelected());
    }

    @Test
    void show_nonExistentCode_404() {
        when(questionQueryUseCase.getQuestion("nonexistentcode")).thenThrow(new NotFoundException("Question not found with code: nonexistentcode"));
        webClient
                .get()
                .uri("/questions/nonexistentcode")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Question not found with code: nonexistentcode");
    }

    @Test
    void create_validQuestionCommand_questionResponse() {
        QuestionCommand command = QuestionCommand.builder()
                .username(EXISTENT_USERNAME)
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .build();
        when(questionCommandUseCase.createQuestion(command)).thenReturn(questionResponses.get(0));
        webClient
                .post()
                .uri("/questions")
                .body(Mono.just(command), QuestionCommand.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.username").isEqualTo(EXISTENT_USERNAME)
                .jsonPath("$.code").isNotEmpty()
                .jsonPath("$.title").isEqualTo("title1")
                .jsonPath("$.content").isEqualTo("content1")
                .jsonPath("$.createdAt").isNotEmpty()
                .jsonPath("$.tags.[0]").isEqualTo("tag1")
                .jsonPath("$.tags.[1]").isEqualTo("tag2");
    }

    @Test
    void create_questionCommandWithNoUsername_422() {
        QuestionCommand command = QuestionCommand.builder()
                .username(null)
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .build();
        webClient
                .post()
                .uri("/questions")
                .body(Mono.just(command), QuestionCommand.class)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Validation failed")
                .jsonPath("$.errors.username").isEqualTo("must not be blank");
    }

    @Test
    void create_questionCommandWithNoTitle_422() {
        QuestionCommand command = QuestionCommand.builder()
                .username(EXISTENT_USERNAME)
                .title("")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .build();
        webClient
                .post()
                .uri("/questions")
                .body(Mono.just(command), QuestionCommand.class)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Validation failed")
                .jsonPath("$.errors.title").isEqualTo("must not be blank");
    }

    @Test
    void createAnswer_validAnswerCommand_questionDetailResponse() {
        AnswerCommand command = AnswerCommand.builder()
                .username(EXISTENT_USERNAME)
                .content("content")
                .build();
        when(questionCommandUseCase.createAnswer("code1", command)).thenReturn(questionDetailResponse1);
        webClient
                .post()
                .uri("/questions/code1/answers")
                .body(Mono.just(command), QuestionCommand.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.username").isEqualTo(EXISTENT_USERNAME)
                .jsonPath("$.code").isEqualTo(questionDetailResponse1.getCode())
                .jsonPath("$.title").isEqualTo(questionDetailResponse1.getTitle())
                .jsonPath("$.content").isEqualTo(questionDetailResponse1.getContent())
                .jsonPath("$.createdAt").isEqualTo(questionDetailResponse1.getCreatedAt().toString())
                .jsonPath("$.tags.[0]").isEqualTo(questionDetailResponse1.getTags().get(0))
                .jsonPath("$.tags.[1]").isEqualTo(questionDetailResponse1.getTags().get(1))
                .jsonPath("$.answers.[0].code").isEqualTo(questionDetailResponse1.getAnswers().get(0).getCode())
                .jsonPath("$.answers.[0].username").isEqualTo(questionDetailResponse1.getAnswers().get(0).getUsername())
                .jsonPath("$.answers.[0].content").isEqualTo(questionDetailResponse1.getAnswers().get(0).getContent())
                .jsonPath("$.answers.[0].createdAt").isEqualTo(questionDetailResponse1.getAnswers().get(0).getCreatedAt().toString())
                .jsonPath("$.answers.[0].selected").isEqualTo(questionDetailResponse1.getAnswers().get(0).getSelected());
    }

    @Test
    void createAnswer_answerCommandWithNoUsername_422() {
        AnswerCommand command = AnswerCommand.builder()
                .username(null)
                .content("content")
                .build();
        webClient
                .post()
                .uri("/questions/code1/answers")
                .body(Mono.just(command), QuestionCommand.class)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Validation failed")
                .jsonPath("$.errors.username").isEqualTo("must not be blank");
    }

    @Test
    void createAnswer_answerCommandWithNoContent_422() {
        AnswerCommand command = AnswerCommand.builder()
                .username(EXISTENT_USERNAME)
                .content(null)
                .build();
        webClient
                .post()
                .uri("/questions/code1/answers")
                .body(Mono.just(command), QuestionCommand.class)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Validation failed")
                .jsonPath("$.errors.content").isEqualTo("must not be blank");
    }

    @Test
    void delete_questionCode_204() {
        webClient
                .delete()
                .uri("/questions/code1")
                .exchange()
                .expectStatus().isNoContent();
        verify(questionCommandUseCase, times(1)).deleteQuestion("code1");
    }
    
}
