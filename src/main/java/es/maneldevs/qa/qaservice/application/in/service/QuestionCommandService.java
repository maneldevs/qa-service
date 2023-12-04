package es.maneldevs.qa.qaservice.application.in.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.maneldevs.libs.exceptionhandling.NotFoundException;
import es.maneldevs.qa.qaservice.application.in.QuestionCommandUseCase;
import es.maneldevs.qa.qaservice.application.model.command.AnswerCommand;
import es.maneldevs.qa.qaservice.application.model.command.QuestionCommand;
import es.maneldevs.qa.qaservice.application.model.response.QuestionDetailResponse;
import es.maneldevs.qa.qaservice.application.model.response.QuestionResponse;
import es.maneldevs.qa.qaservice.application.out.QuestionPort;
import es.maneldevs.qa.qaservice.domain.Answer;
import es.maneldevs.qa.qaservice.domain.Question;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionCommandService implements QuestionCommandUseCase {
    private final QuestionPort questionPort;

    @Override
    public QuestionResponse createQuestion(@Valid QuestionCommand command) {
        Question question = Question.builder()
                .code(UUID.randomUUID().toString())
                .title(command.getTitle())
                .content(command.getContent())
                .username(command.getUsername())
                .createdAt(LocalDateTime.now())
                .tags(command.getTags())
                .build();
        questionPort.save(question);
        return new QuestionResponse(question);
    }

    @Override
    public QuestionDetailResponse createAnswer(String questionCode, @Valid AnswerCommand command) {
        Question question = questionPort.findByCode(questionCode)
                .orElseThrow(() -> new NotFoundException("Question not found with code: " + questionCode));
        Answer answer = Answer.builder()
                .code(UUID.randomUUID().toString())
                .content(command.getContent())
                .username(command.getUsername())
                .createdAt(LocalDateTime.now())
                .selected(false)
                .build();
        question.addAnswer(answer);
        questionPort.save(question);
        return new QuestionDetailResponse(question);
    }

    @Override
    public void deleteQuestion(String questionCode) {
        questionPort.findByCode(questionCode).ifPresent(q -> questionPort.delete(q));
    }
    
}
