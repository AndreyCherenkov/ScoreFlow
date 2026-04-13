package ru.andreycherenkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class ScoreFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreFlowApplication.class, args);
	}

}
