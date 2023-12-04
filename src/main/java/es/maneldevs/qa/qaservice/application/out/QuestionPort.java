package es.maneldevs.qa.qaservice.application.out;

import java.util.List;
import java.util.Optional;

import es.maneldevs.qa.qaservice.domain.Question;

public interface QuestionPort {
    List<Question> findByUsername(String username);
    Optional<Question> findByCode(String code);
    Question save(Question question);
    void delete(Question question);
}
