package kr.co.farmstory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.farmstory.service.UserService;
import kr.co.farmstory.vo.KakaoProfile;
import kr.co.farmstory.vo.OAuthToken;
import kr.co.farmstory.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
@Slf4j
public class KakaoLoginController {

    @Autowired
    private UserService service;


    // ResponseBody = 데이터를 리턴해 주는 컨트롤러 함수
    @GetMapping("auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){

        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTtpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "204893eb5608d9236bf35e3f7a67405f");
        params.add("redirect_uri", "http://localhost:8080/Farmstory/auth/kakao/callback");
        params.add("code", code);
        params.add("client_secret", "X38sk9OGYZZLqqGcbnCHbEUF3JXQSwOQ");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokneRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - Post 방식으로 + response 변수 응답 받기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokneRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("카카오 액세스 토큰: " + oauthToken.getAccess_token());

        // POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt2 = new RestTemplate();

        // HttpHeaders 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청하기 - Post 방식으로 + response 변수 응답 받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // User 오브젝트 : uid, pass, email
        System.out.println("카카오 아이디(번호): "+kakaoProfile.getId());
        System.out.println("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());

        System.out.println("Farmstory 서버 아이디 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("Farmstory 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("Farmstory 서버 패스워드 : " + kakaoProfile.getId() + "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC");


        // pass 인코딩?
        UserVO kakaoUser = UserVO.builder()
                .uid(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .pass(kakaoProfile.getId() + "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC")
                .email(kakaoProfile.getKakao_account().getEmail())
                .build();

        // 기존 가입 여부 체크
        UserVO originUser =  service.selectUser(kakaoUser.getUid());    // 기존 가입자

        if(originUser.getUid() == null){
            System.out.println("기존 회원이 아닙니다 - 자동 회원가입 진행");
            service.insertUser(kakaoUser);
        }

        // 로그인 처리
        // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUSer));
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        //application.yml에 cos: key:cos1234 추가
        //cos:
        //  key: cos1234

        return "회원가입 및 로그인 처리 완료";

    }


}
