package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.user.dto.req.FilterDtoReq;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.Filter;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import com.kusitms.hotsixServer.domain.user.repository.FilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserFilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import com.kusitms.hotsixServer.global.config.jwt.RedisDao;
import com.kusitms.hotsixServer.global.config.jwt.TokenProvider;
import com.kusitms.hotsixServer.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;
import static com.kusitms.hotsixServer.global.error.ErrorCode.SET_FILTER_ERROR;
import static com.kusitms.hotsixServer.global.error.ErrorCode.Token_Error;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final TokenProvider tokenProvider;

    private final FilterRepository filterRepository;

    private final UserRepository userRepository;

    private final RedisDao redisDao;

    private final UserFilterRepository userFilterRepository;


    //accessToken 재발급
    @Transactional
    public UserDto.TokenRes reissue(String rtk) {
        String username = tokenProvider.getRefreshTokenInfo(rtk);
        String rtkInRedis = redisDao.getValues(username);

        if (Objects.isNull(rtkInRedis) || !rtkInRedis.equals(rtk))
            throw new BaseException(Token_Error);

        return UserDto.TokenRes.response(tokenProvider.reCreateToken(username), null);
    }

    //취향 카테고리 선택
    @Transactional
    public void setFilter(FilterDtoReq dto){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();

        if(dto.getFilters().length>2) throw new BaseException(SET_FILTER_ERROR);

        for(int i=0; i<dto.getFilters().length; i++){
            Filter getFilter = filterRepository.findByName(dto.getFilters()[i]).orElseThrow();
            UserFilter userFilter = new UserFilter(getFilter, user);
            userFilterRepository.save(userFilter);
        }
    }
}
