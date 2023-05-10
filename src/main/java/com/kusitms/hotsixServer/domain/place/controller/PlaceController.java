package com.kusitms.hotsixServer.domain.place.controller;


import com.kusitms.hotsixServer.domain.place.constant.PlaceConstants;
import com.kusitms.hotsixServer.domain.place.dto.Category1Response;
import com.kusitms.hotsixServer.domain.place.dto.PlaceDetail;
import com.kusitms.hotsixServer.domain.place.service.PlaceCategoryService;
import com.kusitms.hotsixServer.domain.place.service.PlaceDetailService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/place")
public class PlaceController {

    private final PlaceCategoryService placeCategoryService;
    private final PlaceDetailService placeDetailService;

    public PlaceController(PlaceCategoryService placeCategoryService,
                           PlaceDetailService placeDetailService) {
        this.placeCategoryService = placeCategoryService;
        this.placeDetailService = placeDetailService;
    }

    @ApiOperation("카테고리1(음식점, 카페, 놀거리) 선택시 장소 출력 코드")
    @GetMapping("/list/{category1Id}")
    public ResponseEntity<Category1Response> getPlacesByCategory1(@PathVariable("category1Id") Long category1Id) {
        Category1Response placeResponse = placeCategoryService.getPlacesByCategory1Response(category1Id);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_CATEGORY1_SUCCESS.getMessage(),
                placeResponse).getData());
    }

    @ApiOperation("장소 상세 정보 조회")
    @GetMapping("/detail/{placeId}")
    public ResponseEntity<PlaceDetail> getPlaceDetail(@PathVariable("placeId") Long placeId) {
        PlaceDetail placeDetail = placeDetailService.getPlaceDetail(placeId);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_DETAIL_SUCCESS.getMessage(),
                placeDetail).getData());
    }
}
