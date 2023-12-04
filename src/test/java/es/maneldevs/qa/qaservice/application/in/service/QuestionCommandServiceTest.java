package es.maneldevs.qa.qaservice.application.in.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import es.maneldevs.qa.qaservice.application.out.QuestionPort;
import es.maneldevs.qa.qaservice.domain.Answer;
import es.maneldevs.qa.qaservice.domain.Question;

@ExtendWith(MockitoExtension.class)
public class QuestionCommandServiceTest {
    private Question question;
    @Mock
    private QuestionPort questionPort;
    @InjectMocks
    private QuestionCommandService serviceUnderTest;

    @BeforeEach
    void beforeEach() {
        Answer answer1 = Answer.builder()
                .code("acode1")
                .username("ausername1")
                .content("acontent1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .selected(false)
                .build();
        question = Question.builder()
                .id("1")
                .version(0)
                .code("code1")
                .username("username")
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(new ArrayList<>(List.of("tag1", "tag2")))
                .answers(new ArrayList<>(List.of(answer1)))
                .build();
    }

    @Test
    void createAnswer_answerCommandAndExistentQuestion_questionDetailResponse() {
        AnswerCommand command = AnswerCommand.builder()
                .username("otherusername")
                .content("othercontent").build();
        when(questionPort.findByCode("code1")).thenReturn(Optional.of(question));
        QuestionDetailResponse result = serviceUnderTest.createAnswer("code1", command);
        assertEquals(question.getCode(), result.getCode());
        assertEquals(question.getTitle(), result.getTitle());
        assertEquals(question.getContent(), result.getContent());
        assertEquals(question.getUsername(), result.getUsername());
        assertEquals(question.getCreatedAt(), result.getCreatedAt());
        assertTrue(question.getTags().contains(result.getTags().get(0)));
        assertTrue(question.getTags().contains(result.getTags().get(1)));
        assertEquals(2, result.getAnswers().size());
        assertEquals(command.getContent(), result.getAnswers().get(1).getContent());
        assertEquals(command.getUsername(), result.getAnswers().get(1).getUsername());
        assertNotNull(result.getAnswers().get(1).getCode());
        assertNotNull(result.getAnswers().get(1).getCreatedAt());
        assertFalse(result.getAnswers().get(1).getSelected());
    }

    @Test
    void createAnswer_answerNonxistentQuestion_notFoundException() {
        AnswerCommand command = AnswerCommand.builder()
                .username("username")
                .content("content").build();
        when(questionPort.findByCode("noexistentcode")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> serviceUnderTest.createAnswer("noexistentcode", command));

    }

    @Test
    void createQuestion_questionCommand_questionResponse() {
        QuestionCommand command = QuestionCommand.builder()
                .username("username")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .title("title").build();
        QuestionResponse result = serviceUnderTest.createQuestion(command);
        assertNotNull(result.getCode());
        assertNotNull(result.getCreatedAt());
        assertEquals(command.getUsername(), result.getUsername());
        assertEquals(command.getTitle(), result.getTitle());
        assertEquals(command.getContent(), result.getContent());
        assertEquals(command.getTags(), result.getTags());
        verify(questionPort).save(any(Question.class));
    }

    @Test
    void delete_existentQuestion_void() {
        when(questionPort.findByCode("code1")).thenReturn(Optional.of(question));
        serviceUnderTest.deleteQuestion("code1");
        verify(questionPort, times(1)).delete(question);
    }

    @Test 
    void delete_nonExistentQuestion_void() {
        when(questionPort.findByCode("nonexistentcode")).thenReturn(Optional.empty());
        serviceUnderTest.deleteQuestion("nonexistentcode");
        verify(questionPort, never()).delete(question);
    }
}
