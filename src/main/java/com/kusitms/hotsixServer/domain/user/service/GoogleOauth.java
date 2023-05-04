package com.kusitms.hotsixServer.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.hotsixServer.domain.user.dto.GoogleOauthToken;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOauth {

    @Value("${app.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${app.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${app.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${app.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    @Value("${app.google.userinfo.url}")
    private String GOOGLE_SNS_USERINFO_URL;

    private final ObjectMapper objectMapper;

    public ResponseEntity<String> requestAccessToekn(String code) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    public GoogleOauthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("response.getBody() = " + response.getBody());
        GoogleOauthToken googleOauthToken= objectMapper.readValue(response.getBody(),GoogleOauthToken.class);
        return googleOauthToken;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOauthToken oAuthToken) {

        RestTemplate restTemplate = new RestTemplate();
        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_SNS_USERINFO_URL, HttpMethod.GET,request,String.class);
        log.info("response.getHeaders() = " + response.getHeaders());
        log.info("responseEntity.getStatusCode()"+ response.getStatusCode());
        log.info("response.getBody() = " + response.getBody());
        return response;
    }

    public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {
        log.info("response.getBody() = "+userInfoResponse.getBody());
        GoogleUser googleUser =objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
        return googleUser;
    }

}
