package io.jianxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.jianxun.common.repository.EntityRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = EntityRepositoryImpl.class)
@EnableSpringDataWebSupport
public class WjZbglApplication {

	public static void main(String[] args) {
		SpringApplication.run(WjZbglApplication.class, args);
	}
}
