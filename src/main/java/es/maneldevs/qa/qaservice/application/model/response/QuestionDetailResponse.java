package es.maneldevs.qa.qaservice.application.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionDetailResponse extends QuestionResponse {
    private final List<AnswerResponse> answers;

    @Builder(builderMethodName = "detailBuilder")
    public QuestionDetailResponse(String code, String username, String title, String content, LocalDateTime createdAt,
            List<String> tags, List<AnswerResponse> answers) {
        super(code, username, title, content, createdAt, tags);
        this.answers = answers;
    }
}
