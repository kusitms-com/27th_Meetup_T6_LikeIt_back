package com.kusitms.hotsixServer.domain.user.controller;

import com.kusitms.hotsixServer.domain.user.constant.UserConstants;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.service.MyPageService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserDto.userInfoResponse>> getUserInfo(){
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.GET_USERINFO_SUCCESS.getMessage(),
                this.myPageService.getUserInfo()));
    }
}
