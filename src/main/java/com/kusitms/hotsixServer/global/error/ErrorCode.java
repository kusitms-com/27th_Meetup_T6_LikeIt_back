package com.kusitms.hotsixServer.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * 에러 코드 관리
 */
@Getter
public enum ErrorCode {
    Token_Error(HttpStatus.BAD_REQUEST,400, "토큰값 다시 보내주세요."),
    SET_FILTER_ERROR(HttpStatus.BAD_REQUEST,400, "필터값 2개 이하로 보내주세요."),
    CATEGORY1_ERROR(HttpStatus.BAD_REQUEST,400, "존재하지 않는 카테고리값입니다."),
    PLACE_ERROR(HttpStatus.BAD_REQUEST,400, "존재하지 않는 장소값입니다."),
    STICKER_ERROR(HttpStatus.BAD_REQUEST,400, "스티커값을 다시 확인해주세요."),
    CONVERT_FILE_ERROR(HttpStatus.BAD_REQUEST,400, "파일 변환에 실패하였습니다."),
    UPLOAD_IMAGE_ERROR(HttpStatus.BAD_REQUEST,400, "이미지 업로드에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;



    ErrorCode(HttpStatus httpStatus, int code, String message) { // BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}