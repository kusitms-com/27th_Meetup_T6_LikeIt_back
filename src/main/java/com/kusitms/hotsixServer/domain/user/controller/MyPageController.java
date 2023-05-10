package com.kusitms.hotsixServer.domain.user.controller;

import com.kusitms.hotsixServer.domain.user.constant.UserConstants;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.service.MyPageService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @ApiOperation("회원 정보 보여주기")
    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserDto.userInfoResponse>> getUserInfo(){
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.GET_USERINFO_SUCCESS.getMessage(),
                this.myPageService.getUserInfo()));
    }

    @ApiOperation("회원 정보 수정")
    @PatchMapping("/info")
    public ResponseEntity<ResponseDto> patchUserInfo(@Validated@RequestPart(value = "data") UserDto.updateInfo updateInfo, @RequestPart(value="file", required = false) MultipartFile multipartFile){
        this.myPageService.updateUserInfo(updateInfo,multipartFile);
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.UPDATE_USERINFO.getMessage()));
    }
}
