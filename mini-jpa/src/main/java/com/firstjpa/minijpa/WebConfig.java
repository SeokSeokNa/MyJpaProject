package com.firstjpa.minijpa;

import com.firstjpa.minijpa.access_token.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
    //외부경로를 url로 들어가기
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") //localhost:8080/images/파일명 하면 이미지가 나옴
                .addResourceLocations("file:///C:/fileupload/");//윈도우 용
//                .addResourceLocations("file:///images/"); //도커 용
    }

    //인터셉터 설정부
    @Autowired
    JwtInterceptor jwtInterceptor;
    //인터셉터 설정부
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/api/login") //제외할 url패턴
                .addPathPatterns("/api/**"); //적용할 url패턴
    }
}
