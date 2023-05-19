package com.kusitms.hotsixServer.domain.user.service;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import com.kusitms.hotsixServer.domain.user.dto.req.IdTokenReq;
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


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Collections;

import static com.kusitms.hotsixServer.global.error.ErrorCode.INVALID_TOKEN_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final RedisDao redisDao;

    @Value("${app.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;

    public UserDto.SocialLoginRes checkUserInDB(GoogleUser googleUser) {

        //회원가입
        if (!userRepository.existsByUserEmail(googleUser.getEmail())) {

            User newUser =  new User(googleUser, passwordEncoder);
            Long id = userRepository.save(newUser).getId();
            boolean isSignUp = true;
            return oauthLogin(isSignUp, newUser.getUserEmail(), id);

        } else {  // 로그인
            User user = userRepository.findByUserEmail(googleUser.getEmail()).orElseThrow();
            boolean isSignUp = false;
            return oauthLogin(isSignUp, user.getUserEmail(), user.getId());
        }

    }

    public UserDto.SocialLoginRes oauthLogin(boolean isSignUp, String email, Long id) {

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

        return UserDto.SocialLoginRes.response(
                id, isSignUp, atk, rtk
        );
    }

    @Transactional
    public UserDto.SocialLoginRes appGoogleLogin(IdTokenReq idTokenReq) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_SNS_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenReq.getIdToken());

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
