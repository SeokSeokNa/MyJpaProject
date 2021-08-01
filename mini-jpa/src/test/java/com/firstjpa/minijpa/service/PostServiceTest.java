package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.controller.Form.PostForm;
import com.firstjpa.minijpa.domain.PostWrite;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.PostRepository;
import com.firstjpa.minijpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setUserId("test1");
        userRepository.save(user);

        PostForm postForm = new PostForm();
        postForm.setTitle("제목");
        postForm.setContents("내용내용");

        PostWrite post = PostWrite.createPost(user, postForm.getTitle(), postForm.getContents());
        postRepository.save(post);

        Assertions.assertEquals("제목" , post.getTitle());
    }
}