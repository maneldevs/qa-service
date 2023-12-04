package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;
import java.util.List;

import es.maneldevs.qa.qaservice.domain.Question;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class QuestionDetailResponse extends QuestionResponse {
    private final List<AnswerResponse> answers;

    @Builder(builderMethodName = "detailBuilder")
    public QuestionDetailResponse(String code, String username, String title, String content, LocalDateTime createdAt,
            List<String> tags, List<AnswerResponse> answers) {
        super(code, username, title, content, createdAt, tags);
        this.answers = answers;
    }

    @Builder(builderMethodName = "detailFromQuestionBuilder")
    public QuestionDetailResponse(Question question) {
        super(question.getCode(), question.getUsername(), question.getTitle(), question.getContent(),
                question.getCreatedAt(), question.getTags());
        this.answers = question.getAnswers().stream().map(AnswerResponse::new).toList();
    }
}
