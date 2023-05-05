package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.user.constant.UserConstants;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.exception.TokenErrorException;
import com.kusitms.hotsixServer.global.config.jwt.RedisDao;
import com.kusitms.hotsixServer.global.config.jwt.TokenProvider;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final TokenProvider tokenProvider;

    private final RedisDao redisDao;


    //accessToken 재발급
    @Transactional
    public UserDto.tokenResponse reissue(String rtk) {
        String username = tokenProvider.getRefreshTokenInfo(rtk);
        String rtkInRedis = redisDao.getValues(username);

        if (Objects.isNull(rtkInRedis) || !rtkInRedis.equals(rtk))
            throw new TokenErrorException();

        return UserDto.tokenResponse.response(tokenProvider.reCreateToken(username), null);
    }
}
