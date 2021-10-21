package com.firstjpa.minijpa.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Configuration
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
        exception.printStackTrace();

        String msg="";
        if(exception instanceof AuthenticationServiceException) {
            request.setAttribute("LoginFailMessage", "죄송합니다. 시스템에 오류가 발생했습니다.");
            msg += "죄송합니다. 시스템에 오류가 발생했습니다.";
        }
        else if(exception instanceof BadCredentialsException) {
            request.setAttribute("LoginFailMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            msg += "아이디 또는 비밀번호가 일치하지 않습니다.";
        }
        else if(exception instanceof DisabledException) {
            request.setAttribute("LoginFailMessage", "현재 사용할 수 없는 계정입니다.");
            msg += "현재 사용할 수 없는 계정입니다.";
        }
        else if(exception instanceof LockedException) {
            request.setAttribute("LoginFailMessage", "현재 잠긴 계정입니다.");
            msg += "현재 잠긴 계정입니다.";
        }
        else if(exception instanceof AccountExpiredException) {
            request.setAttribute("LoginFailMessage", "이미 만료된 계정입니다.");
            msg += "이미 만료된 계정입니다.";
        }
        else if(exception instanceof CredentialsExpiredException) {
            request.setAttribute("LoginFailMessage", "비밀번호가 만료된 계정입니다.");
            msg += "비밀번호가 만료된 계정입니다.";
        }
        else{
            request.setAttribute("LoginFailMessage", "계정을 찾을 수 없습니다.");
            msg += "계정을 찾을 수 없습니다.";
        }


        // UTF-8 인코딩 처리
        msg = URLEncoder.encode(msg, "UTF-8");
        setDefaultFailureUrl("/users/login?error=true&exception=" + msg);

        super.onAuthenticationFailure(request,response,exception);
    }
}
