package com.firstjpa.minijpa.domain;

import com.firstjpa.minijpa.controller.Form.UserForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private Birth birth;



    public static User createUser(UserForm userForm) {
        User user = new User();
        user.setUserId(userForm.getUserId());
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getName());
        user.setGender(userForm.getGender());
        user.setBirth(new Birth(userForm.getYear() , userForm.getMonth() , userForm.getDate()));
        return user;
    }

}
