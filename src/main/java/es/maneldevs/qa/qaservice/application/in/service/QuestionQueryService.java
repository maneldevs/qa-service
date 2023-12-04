package es.maneldevs.qa.qaservice.application.in.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.application.in.QuestionQueryUseCase;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import es.maneldevs.qa.qaservice.application.out.QuestionPort;
import es.maneldevs.qa.qaservice.domain.Question;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionQueryService implements QuestionQueryUseCase {
    private final QuestionPort questionPort;

    @Override
    public List<QuestionResponse> getQuestionsByUsername(String username) {
        List<Question> questions = questionPort.findByUsername(username);
        return questions.stream().map(QuestionResponse::new).toList();
    }

    @Override
    public QuestionDetailResponse getQuestion(String code) {
        Question question = questionPort.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Question not found with code: " + code));
        return new QuestionDetailResponse(question);
    }
}
