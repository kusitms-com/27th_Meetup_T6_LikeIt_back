package com.kusitms.hotsixServer.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.hotsixServer.domain.user.constant.UserConstants;
import com.kusitms.hotsixServer.domain.user.dto.FilterDto;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.service.UserService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //토큰 재발급
    @GetMapping("/regenerateToken")
    public ResponseEntity<ResponseDto<UserDto.tokenResponse>> reissue(
            @RequestHeader(value = "REFRESH_TOKEN") String rtk
    ) {
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.REGENERATE_TOKEN_SUCCESS.getMessage(),
                this.userService.reissue(rtk)));
    }

    //취향 카테고리 선택
    @PostMapping("/setFilters")
    public ResponseEntity<ResponseDto> setFilter(@RequestBody FilterDto filterDto){
        this.userService.setFilter(filterDto);
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.SET_FILTER_SUCCESS.getMessage()));
    }
}
