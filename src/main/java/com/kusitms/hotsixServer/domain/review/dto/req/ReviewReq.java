package com.kusitms.hotsixServer.domain.review.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewReq {

    private Long userId;
    private Long placeId;
    private int starRating;
    private String content;
    private String[] stickers;

}