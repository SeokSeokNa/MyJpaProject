package com.firstjpa.minijpa.controller;

import com.firstjpa.minijpa.controller.Form.UserForm;
import com.firstjpa.minijpa.controller.Form.UserLoginForm;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //UserForm객체 매핑 해서 회원가입 폼으로이동
    @GetMapping("/users/new")
    public String signupForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/signupForm";
    }

    //회원가입
    @PostMapping("/users/new")
    public String signup(@Valid UserForm userForm , BindingResult result , HttpSession session) {
        //중복체크
        if (userService.overlapId(userForm.getUserId()) > 0) {
            FieldError fieldError = new FieldError("userForm", "userId", "중복된 아이디 입니다.");
            result.addError(fieldError);
            return "users/signupForm";
        }

        //Valid 체크
        if (result.hasErrors()) {
            return "users/signupForm";
        }
        User user = User.createUser(userForm);
        session.setAttribute("userinfo" , user);
        userService.join(user);

        return "redirect:/";
    }


    //UserLoginForm객체 매핑 해서 로그인 폼으로이동
    @GetMapping("/users/login")
    public String loginForm(Model model) {
        model.addAttribute("userLoginForm", new UserLoginForm());
        return "users/loginForm";
    }

    //로그인
    @PostMapping("/users/login")
    public String login(@Valid UserLoginForm form ,
                        BindingResult result,
                        HttpSession session ,
                        HttpServletResponse response) {
        log.info("로그인 호출");
        List<User> users = userService.login(form.getUserId(), form.getPassword());
        log.info("체크 = " + form.getIsRememberId());
        if (users.size() > 0) {//존재하는 정보라면
            session.setAttribute("userinfo" , users.get(0)); //세션 만들기
        } else {
            FieldError fieldError = new FieldError("userLoginForm", "userId", "아이디 또는 비밀번호가 맞지 않습니다.");
            result.addError(fieldError);
            return "users/loginForm";
        }

        return "redirect:/";
    }
    
    //로그아웃
    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 호출");
        session.invalidate();

        return "redirect:/";
    }
}
