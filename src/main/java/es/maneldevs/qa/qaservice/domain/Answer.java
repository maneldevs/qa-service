package es.maneldevs.qa.qaservice.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private String code;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private Boolean selected;
}
