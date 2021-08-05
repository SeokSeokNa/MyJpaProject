package com.firstjpa.minijpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@SpringBootApplication
public class MiniJpaApplication {
	public static final String UPLOAD_PATH = "C:\\fileupload";


	public static void main(String[] args) {
		SpringApplication.run(MiniJpaApplication.class, args);
	}

	//JPQL 반환결과가 하나도 없을때 익셉션 막기
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
