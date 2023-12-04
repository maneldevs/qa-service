package es.maneldevs.qa.qaservice;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@DataMongoTest
@Import(com.fasterxml.jackson.databind.ObjectMapper.class)
public abstract class MongoTestBase {
    private static MongoDBContainer dbContainer = new MongoDBContainer("mongo:7.0.3")
            .withEnv("TZ", "UTC");

    static {
        dbContainer.start();
    }

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", dbContainer::getHost);
        registry.add("spring.data.mongodb.port", () -> dbContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.database", () -> "test");
    }
}
