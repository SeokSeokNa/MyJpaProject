package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.controller.Form.UserForm;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;


    @Test
    public void 중복체크() throws Exception {
        //given
        UserForm form1 = createUserForm("nsk7647", "test1234", "김동식");
        UserForm form2 = createUserForm("nsk7647", "test1234", "김동식");
        User user1 = User.createUser(form1);
        User user2 = User.createUser(form2);

        //when
        userService.join(user1);
        Long cnt = userService.overlapId(user1.getUserId());

        //then
        Assertions.assertEquals(1,cnt);

    }

    private UserForm createUserForm(String userId, String password, String name) {
        UserForm form = new UserForm();
        form.setUserId(userId);
        form.setPassword(password);
        form.setName(name);
        return form;
    }


}