package es.maneldevs.qa.qaservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    private String id;
    @Version
    private int version;
    @Indexed(unique = true)
    private String name;
}
