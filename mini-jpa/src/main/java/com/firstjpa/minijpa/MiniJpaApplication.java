package com.firstjpa.minijpa;

import com.firstjpa.minijpa.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
@EnableJpaAuditing
@SpringBootApplication
public class MiniJpaApplication {
	public static final String UPLOAD_PATH = "C:\\fileupload";//윈도우 용
//	public static final String UPLOAD_PATH = "/images/";//도커 용


	public static void main(String[] args) {
		SpringApplication.run(MiniJpaApplication.class, args);
	}

	//JPQL 반환결과가 하나도 없을때 익셉션 막기
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}

	//Auditing 처리(등록시간 , 수정시간 , 등록자 , 수정자 등등 ..)
	@Bean
	public AuditorAware<String> auditorProvider(){
		return () -> {
			HttpServletRequest session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			System.out.println("session = " + session.getSession().getAttribute("userinfo"));
			User userinfo = (User) session.getSession().getAttribute("userinfo");
			if (userinfo != null)
				return Optional.of(userinfo.getUserId());
			else
				return Optional.of("guest");

		};
	}

}
