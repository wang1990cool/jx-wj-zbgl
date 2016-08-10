package io.jianxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.jianxun.common.repository.EntityRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = EntityRepositoryImpl.class)
public class WjZbglApplication {

	public static void main(String[] args) {
		SpringApplication.run(WjZbglApplication.class, args);
	}
}
