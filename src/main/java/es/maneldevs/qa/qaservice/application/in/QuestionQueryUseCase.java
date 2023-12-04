package es.maneldevs.qa.qaservice.application.in;

import java.util.List;

import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;

public interface QuestionQueryUseCase {
    List<QuestionResponse> getQuestionsByUsername(String username);
    QuestionDetailResponse getQuestion(String code);
}
