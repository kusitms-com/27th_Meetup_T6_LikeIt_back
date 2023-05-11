package com.kusitms.hotsixServer.domain.place.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PlaceConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        RESPONSE_PLACE_CATEGORY1_SUCCESS("카테고리1 관련 장소 관련도순 출력을 성공하였습니다"),
        RESPONSE_PLACE_DETAIL_SUCCESS("장소 상세 내용 출력을 성공하였습니다");
        private final String message;
    }
}
