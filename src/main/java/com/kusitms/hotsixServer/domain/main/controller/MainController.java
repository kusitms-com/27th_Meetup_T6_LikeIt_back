package com.kusitms.hotsixServer.domain.main.controller;

import com.kusitms.hotsixServer.domain.main.constant.MainConstants;
import com.kusitms.hotsixServer.domain.main.dto.req.SearchReq;
import com.kusitms.hotsixServer.domain.main.dto.res.PlaceBookmarkRes;
import com.kusitms.hotsixServer.domain.main.dto.res.PlaceFilterRes;
import com.kusitms.hotsixServer.domain.main.service.MainService;
import com.kusitms.hotsixServer.domain.place.dto.PlaceListDto;
import com.kusitms.hotsixServer.domain.place.dto.req.CategoryPlaceReq;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<ResponseDto<List<PlaceFilterRes>>> getPlacesByFilter(){
        return ResponseEntity.ok(ResponseDto.create(
                MainConstants.EBoardResponseMessage.PLACES_RESPONSE_SUCCESS.getMessage(),
            this.mainService.getPlacesByFilter()));
    }

    @ApiOperation("Top2 가져오기")
    @GetMapping(value="/places/bookmark")
    public ResponseEntity<ResponseDto<PlaceBookmarkRes>> getPlacesByBookmark(){
        return ResponseEntity.ok(ResponseDto.create(
                MainConstants.EBoardResponseMessage.PLACES_RESPONSE_SUCCESS.getMessage(),
                this.mainService.getTopBookmark()));
    }

    @ApiOperation("검색")
    @GetMapping(value="/places/search")
    public ResponseEntity<ResponseDto<List<PlaceListDto.PlaceInfo>>> getPlacesBySearch(@RequestBody SearchReq req){
        return ResponseEntity.ok(ResponseDto.create(
                MainConstants.EBoardResponseMessage.PLACES_RESPONSE_SUCCESS.getMessage(),
                this.mainService.getPlacesBySearch(req)));
    }
}
