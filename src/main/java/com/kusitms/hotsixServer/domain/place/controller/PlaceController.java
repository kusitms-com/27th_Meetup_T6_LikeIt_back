package com.kusitms.hotsixServer.domain.place.controller;


import com.kusitms.hotsixServer.domain.place.constant.PlaceConstants;
import com.kusitms.hotsixServer.domain.place.dto.res.CategoryPlaceRes;
import com.kusitms.hotsixServer.domain.place.dto.res.PlaceDetailRes;
import com.kusitms.hotsixServer.domain.place.dto.req.CategoryPlaceReq;
import com.kusitms.hotsixServer.domain.place.service.PlaceCategoryService;
import com.kusitms.hotsixServer.domain.place.service.PlaceDetailService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("음식점 선택시 메인 화면")
    @PostMapping("/list/restaurant")
    public ResponseEntity<ResponseDto<CategoryPlaceRes>> getPlacesByRestaurant(@RequestBody CategoryPlaceReq dto) {
        CategoryPlaceRes placeResponse = placeCategoryService.getPlacesByCategory1Response(1L, dto);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_RESTAURANT_SUCCESS.getMessage(),
                placeResponse));
    }

    @ApiOperation("카페 선택시 메인 화면")
    @PostMapping("/list/cafe")
    public ResponseEntity<ResponseDto<CategoryPlaceRes>> getPlacesByCafe(@RequestBody CategoryPlaceReq dto) {
        CategoryPlaceRes placeResponse = placeCategoryService.getPlacesByCategory1Response(2L, dto);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_CAFE_SUCCESS.getMessage(),
                placeResponse));
    }

    @ApiOperation("놀거리 선택시 메인 화면")
    @PostMapping("/list/activity")
    public ResponseEntity<ResponseDto<CategoryPlaceRes>> getPlacesByActivity(@RequestBody CategoryPlaceReq dto) {
        CategoryPlaceRes placeResponse = placeCategoryService.getPlacesByCategory1Response(3L, dto);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_ACTIVITY_SUCCESS.getMessage(),
                placeResponse));
    }

    @ApiOperation("장소 상세 정보 조회")
    @GetMapping("/detail/{placeId}")
    public ResponseEntity<ResponseDto<PlaceDetailRes>> getPlaceDetail(@PathVariable("placeId") Long placeId) {
        PlaceDetailRes placeDetailRes = placeDetailService.getPlaceDetail(placeId);

        return ResponseEntity.ok(ResponseDto.create(
                PlaceConstants.EBoardResponseMessage.RESPONSE_PLACE_DETAIL_SUCCESS.getMessage(),
                placeDetailRes));
    }
}
