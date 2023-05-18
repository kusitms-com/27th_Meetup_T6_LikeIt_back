package com.kusitms.hotsixServer.domain.main.controller;

import com.kusitms.hotsixServer.domain.main.constant.MainConstants;
import com.kusitms.hotsixServer.domain.main.dto.res.GetPlaceBookmarkRes;
import com.kusitms.hotsixServer.domain.main.dto.res.GetPlaceFilterRes;
import com.kusitms.hotsixServer.domain.main.service.MainService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @ApiOperation("메인 취향필터별 장소 추천")
    @GetMapping(value="/places/filter")
    public ResponseEntity<ResponseDto<List<GetPlaceFilterRes>>> getPlacesByFilter(){
        return ResponseEntity.ok(ResponseDto.create(
                MainConstants.EBoardResponseMessage.PLACES_RESPONSE_SUCCESS.getMessage(),
            this.mainService.getPlacesByFilter()));
    }

    @ApiOperation("Top2 가져오기")
    @GetMapping(value="/places/bookmark")
    public ResponseEntity<ResponseDto<GetPlaceBookmarkRes>> getPlacesByBookmark(){
        return ResponseEntity.ok(ResponseDto.create(
                MainConstants.EBoardResponseMessage.PLACES_RESPONSE_SUCCESS.getMessage(),
                this.mainService.getTopBookmark()));
    }
}
