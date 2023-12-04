package es.maneldevs.qa.qaservice.adapter.output.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import es.maneldevs.qa.qaservice.MongoTestBase;
import es.maneldevs.qa.qaservice.domain.Answer;
import es.maneldevs.qa.qaservice.domain.Question;

public class QuestionRepositoryTest extends MongoTestBase {
    private Question questionSaved;
    @Autowired
    QuestionRepository repositoryUnderTest;
    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    void beforeEach() {
        mongoTemplate.remove(new Query(), "questions");
        Answer answer1 = Answer.builder()
                .code("acode1")
                .username("username")
                .content("acontent1")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
                .selected(false)
                .build();
        Answer answer2 = Answer.builder()
                .code("acode2")
                .username("username")
                .content("acontent2")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
                .selected(true)
                .build();
        Question question = Question.builder()
                .code("code1")
                .title("title1")
                .content("content1")
                .username("username")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
                .tags(List.of("tag1", "tag2"))
                .answers(new ArrayList<>(List.of(answer1, answer2)))
                .build();
        questionSaved = mongoTemplate.save(question, "questions");
    }

    @Test
    void findByCode_existentCode_question() {
        Optional<Question> result = repositoryUnderTest.findByCode("code1");
        assertTrue(result.isPresent());
        assertEquals(questionSaved, result.get());
        
    }

    @Test
    void findByCode_nonExistentCode_emptyOptional() {
        Optional<Question> result = repositoryUnderTest.findByCode("codenonexistent");
        assertTrue(result.isEmpty());        
    }

    @Test
    void findByUsername_matchesQuestion_questionList() {
        List<Question> result = repositoryUnderTest.findByUsername("username");
        assertEquals(1, result.size());
        assertTrue(result.contains(questionSaved));
    }

    @Test
    void findByUsername_nonMatchesQuestion_emptyList() {
        List<Question> result = repositoryUnderTest.findByUsername("usernamenonexistent");
        assertEquals(0, result.size());
    }

    @Test
    void save_existentQuestionAddingnewAnswer_QuestionUpdated() {
        Answer newAnswer = Answer.builder()
                .code("newAnswer")
                .username("newUsername")
                .content("newcontent")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
                .selected(false)
                .build();
        questionSaved.getAnswers().add(newAnswer);
        Question result = repositoryUnderTest.save(questionSaved);
        Question questionUpdated = mongoTemplate.findById(questionSaved.getId(), Question.class);
        assertEquals(3, questionUpdated.getAnswers().size());
        assertEquals(questionUpdated.getAnswers().get(2).getCode(), newAnswer.getCode());
        assertEquals(questionSaved.getId(), result.getId());
        assertEquals(questionSaved.getCode(), questionUpdated.getCode());
        assertEquals(questionSaved.getUsername(), questionUpdated.getUsername());
        assertEquals(questionSaved.getContent(), questionUpdated.getContent());
        assertEquals(questionSaved.getCreatedAt(), questionUpdated.getCreatedAt());
        assertTrue(questionSaved.getTags().contains(questionUpdated.getTags().get(0)));
        assertTrue(questionSaved.getTags().contains(questionUpdated.getTags().get(1)));
    }

    @Test
    void save_newQuestion_QuestionCreated() {
        Question newQuestion = Question.builder()
                .code("newcode")
                .title("newtitle")
                .content("newcontent")
                .username("newusername")
                .createdAt(LocalDateTime.of(2023, 1, 1, 1, 1))
                .tags(List.of("tag1", "tag2"))
                .build();
        Question result = repositoryUnderTest.save(newQuestion);
        Question newQuestionSaved = mongoTemplate.findById(result.getId(), Question.class);
        assertNotNull(newQuestionSaved);
        assertEquals(newQuestion.getCode(), newQuestionSaved.getCode());
        assertEquals(newQuestion.getTitle(), newQuestionSaved.getTitle());
        assertEquals(newQuestion.getContent(), newQuestionSaved.getContent());
        assertEquals(newQuestion.getUsername(), newQuestionSaved.getUsername());
        assertEquals(newQuestion.getCreatedAt(), newQuestionSaved.getCreatedAt());
        assertTrue(newQuestion.getTags().contains(newQuestionSaved.getTags().get(0)));
        assertTrue(newQuestion.getTags().contains(newQuestionSaved.getTags().get(1)));
        assertTrue(newQuestionSaved.getAnswers().isEmpty());
    }

    @Test
    void delete_question_void() {
        repositoryUnderTest.delete(questionSaved);
        Question result = mongoTemplate.findById(questionSaved.getId(), Question.class);
        assertNull(result);
    }

}
