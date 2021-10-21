package com.firstjpa.minijpa.security;

import com.firstjpa.minijpa.security.PrincipalDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    public SecurityConfig(PrincipalDetailsService principalDetailsService) {
        this.principalDetailsService = principalDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder encodePWD(){ //비밀번호 암호화를 위해 사용 시큐리티는 비밀번호가 암호화 되있어야 사용가능하다
        return new BCryptPasswordEncoder();   //회원가입할때 쓰면된다.
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채는데
    // 해당 password가 뭘로 해쉬화해서 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        // /css/**, /images/**, /js/** 등 정적 리소스는 보안필터를 거치지 않게 한다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("인가요청");
        http.csrf().disable()//csrf토큰 비활성화(테스트시 걸어두는게 좋음) 시큐리티는 csrf토큰이 있어야 접근가능함
                .authorizeRequests() //인가 요청이 오면
                    .antMatchers("/board/**").hasRole("ADMIN") // "/board" 로 접근하는 것은 모두 매니저 권한만 접근가능하고
                    .anyRequest() //다른 모든 요청은
                    .permitAll() //접근을 허용한다.
                    .and() // 그리고
                .formLogin() //로그인 폼은
                    .loginPage("/users/login") //로그인 페이지를 우리가 만든 페이지로 등록한다.
                    .loginProcessingUrl("/users/auth")// 로그인 성공 시 이동할 경로.
                    .defaultSuccessUrl("/") //정상일떄
                    .usernameParameter("userId") //username에 해당하는 파라미터명
                    .passwordParameter("password")//password에 해당하는 파라미터명
                    //.failureUrl("/users/login") //로그인 실패시 이동할 곳
                    .failureHandler(authFailureHandler)//로그인 실패시 처리하는 핸들러(내가 만들었음 !!)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true); //로그아웃 시 인증정보를 지우고 설정된 세션을 무효화 시킨다는 설정

        http.exceptionHandling()
                .accessDeniedPage("/denied"); //권한이 없는 대상이 접속을 시도했을경우

        //중복 로그인
        http.sessionManagement()
                .maximumSessions(1) //세션 최대 허용 수
                .maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.

    }
}
