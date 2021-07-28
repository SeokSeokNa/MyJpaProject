package com.firstjpa.minijpa.controller;

import com.firstjpa.minijpa.controller.Form.UserForm;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String signupForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/signupForm";
    }

    @PostMapping("/users/new")
    public String signup(@Valid UserForm userForm , BindingResult result) {
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
        userService.join(user);

        return "redirect:/";
    }
}
