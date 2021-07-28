package com.firstjpa.minijpa.controller.Form;

import com.firstjpa.minijpa.domain.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class UserForm {
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    //(?=.*[0-9])(?=.*[a-z])(?=.*\W)(?=\S+$).{6,12}
    //비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;
    @NotEmpty(message = "이름은 필수 항목입니다.")
    private String name;

    private Gender gender;

    private String year;
    private String month;
    private String date;
}
