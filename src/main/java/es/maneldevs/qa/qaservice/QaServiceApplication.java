package es.maneldevs.qa.qaservice;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan("es.maneldevs")
public class QaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaServiceApplication.class, args);
	}

	@PostConstruct
    public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
