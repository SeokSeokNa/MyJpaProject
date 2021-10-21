package com.firstjpa.minijpa.security;

import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.UserRepository2;
import com.firstjpa.minijpa.security.PrincipalDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired private UserRepository2 userRepository;

    //스프링이 로그인 요청을 가로챌때 userId, password 변수 2개를 가로채는데
    //(원래는 변수이름이 username , password 인데 내가 SecurityConfig.java 파일에서 설정해줘서 userId , password로 받을 수 있게된것)
    
    //password 부분 처리는 알아서처리,(그래서 DB에서 UserId로만 조회하면 되는것이다 , 조회 해오면 password 부분은 시큐리티가 알아서 비교해서 판단해줌)
    //userId DB에 있는지 확인해줘야함
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("결과 = "+userId);
        User principal = userRepository.findByUserId(userId)
                .orElseThrow(() ->{
                    return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.:" + userId);
                });

        return new PrincipalDetail(principal); //시큐리티의 세션에 유저정보가 저장이됨. (원래는 콘솔창에 뜨는 user, pw가 있었음)
    }
}
