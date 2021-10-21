package com.firstjpa.minijpa.domain;

import com.firstjpa.minijpa.controller.Form.UserForm;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "USER_SEQ_GENERATOR", // 매핑할 데이터베이스 시퀀스 이름
        sequenceName = "USER_SEQ",      // DB에 생성된 시퀀스 이름
        initialValue = 1,                 // DDL 생성시만 사용되며 시작값
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "USER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private Birth birth;

    private String  role;




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
