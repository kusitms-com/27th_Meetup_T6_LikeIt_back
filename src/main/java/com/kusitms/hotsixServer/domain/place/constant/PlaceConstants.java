package com.kusitms.hotsixServer.domain.place.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PlaceConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        RESPONSE_PLACE_RESTAURANT_SUCCESS("음식점 카테고리 장소 출력을 성공하였습니다"),
        RESPONSE_PLACE_CAFE_SUCCESS("카페 카테고리 장소 출력을 성공하였습니다"),
        RESPONSE_PLACE_ACTIVITY_SUCCESS("놀거리 카테고리 장소 출력을 성공하였습니다"),
        RESPONSE_PLACE_DETAIL_SUCCESS("장소 상세 내용 출력을 성공하였습니다"),
        RESPONSE_ADD_BOOKMARK_SUCCESS("북마크 설정을 성공하였습니다"),
        RESPONSE_DELETE_BOOKMARK_SUCCESS("북마크 해제를 성공하였습니다");

        private final String message;
    }
}
