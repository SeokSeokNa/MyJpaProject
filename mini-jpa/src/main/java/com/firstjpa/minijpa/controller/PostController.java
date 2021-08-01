package com.firstjpa.minijpa.controller;

import com.firstjpa.minijpa.controller.Form.PostForm;
import com.firstjpa.minijpa.controller.Form.UserForm;
import com.firstjpa.minijpa.domain.PostWrite;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post/new")
    public String registForm(Model model) {
        model.addAttribute("postForm" , new PostForm());
        return "post/postFormPage";
    }

    //게시글 등록
    @PostMapping("/post/new")
    public String registPost(@Valid PostForm postForm , BindingResult result , HttpSession session) {
        //Valid 체크
        if (result.hasErrors()) {
            return "post/postFormPage";
        }
        User user = (User)session.getAttribute("principal");
        PostWrite post = PostWrite.createPost(user, postForm.getTitle(), postForm.getContents());
        postService.savePost(post);
        return "redirect:/";
    }
}
