package com.kusitms.hotsixServer.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kusitms.hotsixServer.domain.user.dto.GoogleOauthToken;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import com.kusitms.hotsixServer.domain.user.dto.IdTokenDto;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import com.kusitms.hotsixServer.global.config.jwt.RedisDao;
import com.kusitms.hotsixServer.global.config.jwt.TokenProvider;
import com.kusitms.hotsixServer.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Collections;

import static com.kusitms.hotsixServer.global.error.ErrorCode.INVALID_TOKEN_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    private final TokenProvider tokenProvider;

    private final HttpServletResponse response;

    private final PasswordEncoder passwordEncoder;

    private final GoogleOauth googleOauth;

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final RedisDao redisDao;

    @Value("${app.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;

    public UserDto.socialLoginResponse getUserInfo(String code) throws JsonProcessingException {

        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToekn(code);

        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOauthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);

        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

        return checkUserInDB(googleUser);
    }

    public UserDto.socialLoginResponse checkUserInDB(GoogleUser googleUser) {

        //회원가입
        if (!userRepository.existsByUserEmail(googleUser.getEmail())) {

            User newUser = User.createUser(googleUser, passwordEncoder);
            Long id = userRepository.save(newUser).getId();
            boolean isSignUp = true;
            return oauthLogin(isSignUp, newUser.getUserEmail(), id);

        } else {  // 로그인
            User user = userRepository.findByUserEmail(googleUser.getEmail()).orElseThrow();
            boolean isSignUp = false;
            return oauthLogin(isSignUp, user.getUserEmail(), user.getId());
        }

    }

    public UserDto.socialLoginResponse oauthLogin(boolean isSignUp, String email, Long id) {

        // (1) authentication 객체 생성 후 SecurityContext에 등록
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "google");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // (2) 유저 토큰 생성
        String atk = tokenProvider.createToken(authentication);
        String rtk = tokenProvider.createRefreshToken(email);
        redisDao.setValues(email, rtk, Duration.ofDays(14));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + atk);

        return UserDto.socialLoginResponse.response(
                id, isSignUp, atk, rtk
        );
    }

    public void request(String socialLoginType) throws IOException {
        String redirectURL = googleOauth.getOauthRedirectURL();

        response.sendRedirect(redirectURL);
    }

    @Transactional
    public UserDto.socialLoginResponse appGoogleLogin(IdTokenDto idTokenDto) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_SNS_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenDto.getIdToken());

        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            GoogleUser googleUser = new GoogleUser(userId, email, emailVerified, name, givenName, pictureUrl, locale);

            return checkUserInDB(googleUser);
        } else {
            throw new BaseException(INVALID_TOKEN_ERROR);
        }
    }
}
