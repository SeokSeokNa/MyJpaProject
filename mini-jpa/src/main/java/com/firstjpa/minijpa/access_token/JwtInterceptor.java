package com.firstjpa.minijpa.access_token;

import com.firstjpa.minijpa.api_dto.TokenResponseDto;
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
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refreshToken");
        String checkToken;
        if(refreshToken!=null) checkToken = refreshToken;
        else checkToken = accessToken;

        if (checkToken != null && checkToken.length() > 0) {
            try {
                System.out.println("인터페이스 들어옴");
                jwtToken.parseJwtToken(checkToken);
                String userId = jwtToken.getUserId(checkToken);
                request.setAttribute("userId" , userId);
                return true;
            } catch (Exception e) {
                if(checkToken.equals(refreshToken))
                    throw new AuthException("로그인 유지 시간이 끝났습니다. 다시 로그인 해주세요");//리프레시 토큰도 만료되었을 경우
                else
                    throw new AuthException("권한이 없습니다 로그인 이후 이용해주세요!");//토큰 발급을 안받았을 경우
            }
        }
        return false;
    }
}
