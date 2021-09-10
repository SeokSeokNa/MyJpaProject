package com.firstjpa.minijpa.access_token;

import com.firstjpa.minijpa.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object user_name = request.getAttribute("user_name");
        if (user_name != null) {
          pushAlert((String) user_name);
        }

    }

    public void pushAlert(String user_name) throws Exception{
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Authorization","key=AAAA1Ku3HLQ:APA91bEynhOnUesIxioz3kV1R7C9nNHpSpoX8oRIofq0b1NWx8HifoD5MGx1MZztc6CSDG8aTBTDD-i-C9SC1eRhzo4IdEmKwSuzMIOdR0Hw3lBMP4uDTNmc3Y_vGPfAP7dP_RVLoy6K");

        String to = "\\/topics\\/WeAreMemory_topic";
        String replace = to.replace("\\", "");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("to",replace);
        jsonObject.put("prioty", "high");
        JSONObject sub = new JSONObject();
        sub.put("title", "알림!");
        sub.put("body", user_name+" 님이 새로운 글을 작성하셨어용!!");
        jsonObject.put("notification",sub);

        System.out.println(jsonObject);

        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(jsonObject.toJSONString());
        bw.flush();
        bw.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String returnMsg = in.readLine();
        System.out.println("returnMsg = " + returnMsg);

        int responseCode = conn.getResponseCode();
        System.out.println("코드 = "+responseCode);

        System.out.println(user_name.toString());
    }
}
