package com.firstjpa.minijpa.access_token;

import com.firstjpa.minijpa.api.StatusEnum;
import com.firstjpa.minijpa.exception.AuthException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {
    private Long expiredTime = 1000L * 60 *30; // 토큰 유효 시간 (1분)
    private Long refresh_expiredTime = 1000L * 60 * 60 * 24; // 토큰 유효 시간 (24시간) 1000 * 60L*60L*24L
    private String alg_key = "secret";
/*
iss : 토큰 발급자
sub : 토큰의 제목
aud : 토큰의 대상
exp : 토큰 만료일시
nbf : Not Before의 약자이며 지정된 날자가 도달하기 전까지는 토큰이 처리되지 않는다.
iat : 토큰이 발행된(issued) 시간을 의미한다.

UnsupportedJwtException : 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
MalformedJwtException : JWT가 올바르게 구성되어 오지 않았을 때
ExpiredJwtException : JWT를 생성할 때 지정한 유효기간이 초과되었을 때
SignatureException : JWT의 기존 서명을 확인하지 못했을 때
 */

    public String makeJwtToken(String userId , int kind_token) {

        Date ext = new Date(); // 토큰 만료 시간
        if (kind_token == 0) { // accessToken
            ext.setTime(ext.getTime() + expiredTime);
        } else {//refreshToken
            ext.setTime(ext.getTime() + refresh_expiredTime);
        }

        //헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg","HS256");

        //페이로드
        HashMap<String, Object> payloads = new HashMap<>();
        payloads.put("iss", "seok(admin)"); //발급자
        payloads.put("aud",userId);//발급받는 대상
        payloads.put("data", "hello world"); //일반 데이터

        Date now = new Date();
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject(userId)
                .setExpiration(ext) //만료시간
                .signWith(SignatureAlgorithm.HS256, alg_key)
                .compact();
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1) 헤더의 타입(typ)을 지정할 수 있습니다. jwt를 사용하기 때문에 Header.JWT_TYPE로 사용해줍니다.
//                .setIssuer("seok") // (2) 등록된 클레임 중, 토큰 발급자(iss)를 설정할 수 있습니다.
//                .setIssuedAt(now) // (3) 등록된 클레임 중, 발급 시간(iat)를 설정할 수 있습니다. Date 타입만 추가가 가능합니다.
//                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4)등록된 클레임 중, 만료 시간(exp)을 설정할 수 있습니다. 마찬가지로 Date 타입만 추가가 가능합니다
//                .claim("id", "아이디") // (5) 비공개 클레임을 설정할 수 있습니다. (key-value)
//                .claim("email", "ajufresh@gmail.com")
//                .signWith(SignatureAlgorithm.HS256, "secret") // (6) 해싱 알고리즘과 시크릿 키를 설정할 수 있습니다.
//                .compact();
    }


//=====================================================================================================================================================================================
//=====================================================================================================================================================================================
//=======================================================================토큰 검증부 ====================================================================================================
//=====================================================================================================================================================================================

    public Boolean parseJwtToken(String authorizationHeader) {

        try {
            Map<String, Object> claimMap = null;
            Claims claims = Jwts.parser()
                    .setSigningKey(alg_key)
                    .parseClaimsJws(authorizationHeader)
                    .getBody();


            Date exp = claims.get("exp", Date.class);
            System.out.println("exp = " + exp);

            claimMap = claims;
            System.out.println(claimMap);
            return true;
        } catch (UnsupportedJwtException ue) {
            throw  new AuthException(StatusEnum.Unsupport.getMessage());
        } catch (MalformedJwtException me) {
            throw  new AuthException(StatusEnum.MalformedJwt.getMessage());
        } catch (ExpiredJwtException ee) {
            throw  new AuthException(StatusEnum.Expired.getMessage());
        } catch (SignatureException se) {
            throw  new AuthException(StatusEnum.Signature.getMessage());
        } catch (IllegalArgumentException ie) {
            throw  new AuthException(StatusEnum.IllegalArgument.getMessage());
        }

    }


    public String getUserId(String token) {
        String userId = Jwts.parser()
                .setSigningKey(alg_key)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userId;
    }

    public Long getExpiredTime(String token) {
        long time = Jwts.parser()
                .setSigningKey(alg_key)
                .parseClaimsJws(token)
                .getBody().getExpiration().getTime();
        return time;
    }
}
