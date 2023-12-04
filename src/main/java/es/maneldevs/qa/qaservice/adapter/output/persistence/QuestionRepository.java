package es.maneldevs.qa.qaservice.adapter.output.persistence;

import org.springframework.data.repository.CrudRepository;

import es.maneldevs.qa.qaservice.application.out.QuestionPort;
import es.maneldevs.qa.qaservice.domain.Question;

public interface QuestionRepository extends QuestionPort, CrudRepository<Question, String> {
    
}
