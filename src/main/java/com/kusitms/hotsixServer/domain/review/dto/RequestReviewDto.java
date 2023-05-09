package com.kusitms.hotsixServer.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestReviewDto {

    private Long userId;
    private Long placeId;
    private float starRating;
    private String content;
    private String[] stickers;

}