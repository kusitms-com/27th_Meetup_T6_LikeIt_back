package com.kusitms.hotsixServer.domain.main.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MainConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        PLACES_RESPONSE_SUCCESS("장소 데이터 출력 성공하였습니다.");

        private final String message;
    }
}
