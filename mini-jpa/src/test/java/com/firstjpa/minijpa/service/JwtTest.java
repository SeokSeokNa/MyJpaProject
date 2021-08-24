package com.firstjpa.minijpa.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
public class JwtTest {

/*
iss : 토큰 발급자
sub : 토큰의 제목
aud : 토큰의 대상
exp : 토큰 만료일시
nbf : Not Before의 약자이며 지정된 날자가 도달하기 전까지는 토큰이 처리되지 않는다.
iat : 토큰이 발행된(issued) 시간을 의미한다.
 */

    @Test
    public void jwtTest() throws Exception {
        //given
        String token = makeJwtToken();
        System.out.println(token);
        //when
        Claims claims = parseJwtToken("Bearer "+token);
        System.out.println("끝");
        //then
    }



    public String makeJwtToken() {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1) 헤더의 타입(typ)을 지정할 수 있습니다. jwt를 사용하기 때문에 Header.JWT_TYPE로 사용해줍니다.
                .setIssuer("seok") // (2) 등록된 클레임 중, 토큰 발급자(iss)를 설정할 수 있습니다.
                .setIssuedAt(now) // (3) 등록된 클레임 중, 발급 시간(iat)를 설정할 수 있습니다. Date 타입만 추가가 가능합니다.
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4)등록된 클레임 중, 만료 시간(exp)을 설정할 수 있습니다. 마찬가지로 Date 타입만 추가가 가능합니다
                .claim("id", "아이디") // (5) 비공개 클레임을 설정할 수 있습니다. (key-value)
                .claim("email", "ajufresh@gmail.com")
                .signWith(SignatureAlgorithm.HS256, "secret") // (6) 해싱 알고리즘과 시크릿 키를 설정할 수 있습니다.
                .compact();
    }
//    ========================================================
//    ========================================================
//    ========================================================해석하는부분=====================================================================
/*
UnsupportedJwtException : 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
MalformedJwtException : JWT가 올바르게 구서오디지 않았을 때
ExpiredJwtException : JWT를 생성할 때 지정한 유효기간이 초과되었을 때
SignatureException : JWT의 기존 서명을 확인하지 못했을 때
 */


    public Claims parseJwtToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader); // (1) 헤더가 'Bearer'로 시작하는지 검사합니다.
        String token = extractToken(authorizationHeader); // (2)Bearer'을 제외한 문자열만 반환해주도록 처리해줍니다.

        return Jwts.parser()
                .setSigningKey("secret") // (3)시크릿 키를 넣어주어 토큰을 해석할 수 있습니다.
                .parseClaimsJws(token) // (4)해석할 토큰을 문자열(String) 형태로 넣어줍니다.
                .getBody();
    }

    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }




}
