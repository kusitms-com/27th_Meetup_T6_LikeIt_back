package com.kusitms.hotsixServer.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.hotsixServer.domain.user.dto.GoogleOauthToken;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;

    public void oauthLogin(String code) throws JsonProcessingException {

        //(1)구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToekn(code);

        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOauthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);

        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

    }
}
