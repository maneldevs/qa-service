package es.maneldevs.qa.qaservice.application.in.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.adapter.output.persistence.QuestionRepository;
import es.maneldevs.qa.qaservice.application.model.response.AnswerResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import es.maneldevs.qa.qaservice.domain.Answer;
import es.maneldevs.qa.qaservice.domain.Question;

@ExtendWith(MockitoExtension.class)
public class QuestionQueryServiceTest {
    private static final String EXISTENT_USERNAME = "username";
    private static final String EXISTENT_CODE = "code1";
    private List<Question> questions;
    private List<QuestionResponse> questionResponses;
    private List<QuestionDetailResponse> questionDetailResponses;
    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private QuestionQueryService serviceUnderTest;

    @BeforeEach
    void beforeEach() {
        Answer answer1 = Answer.builder()
                .code("acode1")
                .username("ausername1")
                .content("acontent1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .selected(false)
                .build();
        Answer answer2 = Answer.builder()
                .code("acode2")
                .username("ausername2")
                .content("acontent2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .selected(true)
                .build();
        AnswerResponse answerResponse1 = AnswerResponse.builder()
                .code("acode1")
                .username("ausername1")
                .content("acontent1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .selected(false)
                .build();
        AnswerResponse answerResponse2 = AnswerResponse.builder()
                .code("acode2")
                .username("ausername2")
                .content("acontent2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .selected(true)
                .build();
        Question question1 = Question.builder()
                .id("1")
                .version(0)
                .code(EXISTENT_CODE)
                .username(EXISTENT_USERNAME)
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answer1))
                .build();
        Question question2 = Question.builder()
                .id("2")
                .version(0)
                .code("code2")
                .username(EXISTENT_USERNAME)
                .title("title2")
                .content("content2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answer2))
                .build();
        QuestionResponse questionResponse1 = QuestionResponse.builder()
                .code(EXISTENT_CODE)
                .username(EXISTENT_USERNAME)
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .build();
        QuestionResponse questionResponse2 = QuestionResponse.builder()
                .code("code2")
                .username(EXISTENT_USERNAME)
                .title("title2")
                .content("content2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .build();
        QuestionDetailResponse questionDetailResponse1 = QuestionDetailResponse.detailFromQuestionBuilder()
                .code(EXISTENT_CODE)
                .username(EXISTENT_USERNAME)
                .title("title1")
                .content("content1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answerResponse1))
                .build();
        QuestionDetailResponse questionDetailResponse2 = QuestionDetailResponse.detailFromQuestionBuilder()
                .code("code2")
                .username(EXISTENT_USERNAME)
                .title("title2")
                .content("content2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .tags(List.of("tag1", "tag2"))
                .answers(List.of(answerResponse2))
                .build();
        questions = List.of(question1, question2);
        questionResponses = List.of(questionResponse1, questionResponse2);
        questionDetailResponses = List.of(questionDetailResponse1, questionDetailResponse2);
    }

    @Test
    void getQuestionsByUsername_existentUsername_questionResponseList() {
        when(questionRepository.findByUsername(EXISTENT_USERNAME)).thenReturn(questions);
        List<QuestionResponse> result = serviceUnderTest.getQuestionsByUsername(EXISTENT_USERNAME);
        assertEquals(2, result.size());
        assertTrue(result.contains(questionResponses.get(0)));
        assertTrue(result.contains(questionResponses.get(1)));
    }

    @Test
    void getQuestionsByUsername_nonExistentUsername_emptyList() {
        when(questionRepository.findByUsername("nonexistenusername")).thenReturn(List.of());
        List<QuestionResponse> result = serviceUnderTest.getQuestionsByUsername("nonexistenusername");
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuestion_existentCode_questionDetailResponse() {
        when(questionRepository.findByCode(EXISTENT_CODE)).thenReturn(Optional.of(questions.get(0)));
        QuestionDetailResponse result = serviceUnderTest.getQuestion(EXISTENT_CODE);
        assertEquals(questionDetailResponses.get(0), result);
    }

    @Test
    void getQuestion_nonExistentCode_NotFoundException() {
        when(questionRepository.findByCode("nonexistentcode")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> serviceUnderTest.getQuestion("nonexistentcode"));
    }

}
