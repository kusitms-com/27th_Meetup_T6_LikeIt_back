package com.kusitms.hotsixServer.domain.user.controller;

import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import com.kusitms.hotsixServer.domain.user.constant.UserConstants;
import com.kusitms.hotsixServer.domain.user.dto.req.FilterDtoReq;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.service.MyPageService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @ApiOperation("회원 정보 보여주기")
    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserDto.GetUserInfoRes>> getUserInfo(){
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.GET_USERINFO_SUCCESS.getMessage(),
                this.myPageService.getUserInfo()));
    }

    @ApiOperation("회원 정보 수정")
    @PatchMapping("/info")
    public ResponseEntity<ResponseDto> patchUserInfo(@Validated@RequestPart(value = "data") UserDto.UpdateInfoReq updateInfo, @RequestPart(value="file", required = false) MultipartFile multipartFile){
        this.myPageService.updateUserInfo(updateInfo,multipartFile);
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.UPDATE_USERINFO.getMessage()));
    }

    @ApiOperation("취향 카테고리 수정")
    @PatchMapping("/filter")
    public ResponseEntity<ResponseDto> patchfilter(@RequestBody FilterDtoReq filterDtoReq){
        this.myPageService.updateFilters(filterDtoReq);
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.UPDATE_USERFILTER_SUCCESS.getMessage()));
    }

    @ApiOperation("내 리뷰 반환")
    @GetMapping("/review")
    public ResponseEntity<ResponseDto<List<ReviewDto.myReviewRes>>> getReview(){
        return ResponseEntity.ok(ResponseDto.create(
                UserConstants.EBoardResponseMessage.GET_USERREVIEW_SUCCESS.getMessage(),
                this.myPageService.getReviews()));
    }

}
