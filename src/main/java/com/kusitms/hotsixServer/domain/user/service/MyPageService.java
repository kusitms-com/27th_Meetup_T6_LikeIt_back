package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import com.kusitms.hotsixServer.domain.user.repository.UserFilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {


    private final UserRepository userRepository;

    private final UserFilterRepository userFilterRepository;

    //회원 정보 조회
    public UserDto.userInfoResponse getUserInfo(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<String> filters = userFilterRepository.findAllByUserFetchFilter(user)
                .stream()
                .map(userFilter -> userFilter.getFilter().getName())
                .collect(Collectors.toList());

        return UserDto.userInfoResponse.response(user, filters);

    }
}
