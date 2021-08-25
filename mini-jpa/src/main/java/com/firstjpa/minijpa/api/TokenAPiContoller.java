package com.firstjpa.minijpa.api;

import com.firstjpa.minijpa.access_token.JwtToken;
import com.firstjpa.minijpa.api_dto.LoginRequestDto;
import com.firstjpa.minijpa.api_dto.TokenResponseDto;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TokenAPiContoller {
    private final UserRepository2 userRepository;
    private final JwtToken jwtToken;

    @PostMapping("/api/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        //안드로이드에서 넘어온 id, 비밀번호를 가지고 db에서 조회
        List<User> findUser = userRepository.findByUserIdAndPassword(loginRequestDto.getUserId(), loginRequestDto.getPassword());
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        //조회된 정보가 있으면
        try {
            User user = findUser.get(0);
            System.out.println(user.getName());
            message.setStatus(StatusEnum.OK.statusCode);
            message.setMessage(StatusEnum.OK.message);
            message.setUserAccess(new TokenResponseDto(jwtToken.makeJwtToken(user.getUserId()),"bearer" , user.getName()) );
        } catch (Exception e) {
            throw new Exception("아이디 또는 비밀번호가 틀렸습니다");
        }


        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/api/tokenCheck")
    public boolean login(@RequestHeader(value = "Authorization") String  Authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));


        return true;
    }
}
