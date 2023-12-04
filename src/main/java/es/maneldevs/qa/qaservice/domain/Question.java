package es.maneldevs.qa.qaservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    private String id;
    @Version
    private int version;
    @Indexed(unique = true)
    private String code;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @Builder.Default
    private List<String> tags = new ArrayList<>();
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
