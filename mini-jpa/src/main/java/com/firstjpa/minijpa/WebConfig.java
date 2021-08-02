package com.firstjpa.minijpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //외부경로를 url로 들어가기
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") //localhost:8080/images/파일명 하면 이미지가 나옴
                .addResourceLocations("file:///E:/Study/fileupload/");
    }
}
