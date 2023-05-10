package com.kusitms.hotsixServer.domain.review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

    private String nickname;
    private float starRating;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private String[] Stickers;

}
