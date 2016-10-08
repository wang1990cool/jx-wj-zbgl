package io.jianxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import io.jianxun.business.service.AuditorAwareImpl;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.EntityRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = EntityRepositoryImpl.class)
@EnableJpaAuditing
@EnableScheduling
public class WjZbglApplication {

	@Bean
	public AuditorAware<UserDetails> auditorProvider() {
		return new AuditorAwareImpl();
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(WjZbglApplication.class, args);
	}
}
