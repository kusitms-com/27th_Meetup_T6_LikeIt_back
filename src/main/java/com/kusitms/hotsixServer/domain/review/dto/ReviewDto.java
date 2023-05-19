package com.kusitms.hotsixServer.domain.review.dto;

import com.kusitms.hotsixServer.domain.place.dto.PlaceDetailDto;
import lombok.*;


@Getter
@Builder
public class ReviewDto {

    private Long id;
    private String username;
    private String img;
    private float starRating;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private String[] stickers;

    public static ReviewDto from(Long id, String name, String img, float starRating, String content, int likeCount, int dislikeCount, String[] stickers){
        return ReviewDto.builder()
                .id(id)
                .username(name)
                .img(img)
                .starRating(starRating)
                .content(content)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .stickers(stickers)
                .build();
    }
    @Getter
    @Builder
    public static class myReviewRes {
        private ReviewDto reviewDto;
        private PlaceDetailDto.SimplePlaceInfo placeInfo;

        public static myReviewRes from (ReviewDto reviewDto, PlaceDetailDto.SimplePlaceInfo placeInfo) {
           return myReviewRes.builder()
                   .reviewDto(reviewDto)
                   .placeInfo(placeInfo)
                   .build();
        }

    }

}
