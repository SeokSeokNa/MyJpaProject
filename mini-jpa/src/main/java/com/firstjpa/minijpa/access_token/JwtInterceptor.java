package com.firstjpa.minijpa.access_token;

import com.firstjpa.minijpa.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtToken jwtToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.length() > 0) {
            System.out.println("인터페이스 들어옴");
            jwtToken.parseJwtToken(token);
            String userId = jwtToken.getUserId(token);
            request.setAttribute("userId" , userId);
            return true;
        } else {
            throw new AuthException("권한이 없습니다 로그인 이후 이용해주세요!");//토큰 발급을 안받았을 경우
        }

    }
}
