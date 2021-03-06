package com.firstjpa.minijpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("메인 홈 접속");
        return "home";
    }

    @RequestMapping("/denied")
    public String denied() {
        log.info("Denied 접속");
        return "denied";
    }
}
