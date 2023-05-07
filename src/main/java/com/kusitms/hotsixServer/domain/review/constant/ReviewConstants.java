package com.kusitms.hotsixServer.domain.review.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ReviewConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EBoardResponseMessage{
        REQUEST_REVIEW_SUCCESS("리뷰 등록에 성공했습니다"),
        DELETE_REVIEW_SUCCESS("리뷰 삭제에 성공했습니다");
        private final String message;
    }
    @Getter
    @RequiredArgsConstructor
    public enum EToken{
        eRefreshToken("RT:");
        private final String message;
    }
}
