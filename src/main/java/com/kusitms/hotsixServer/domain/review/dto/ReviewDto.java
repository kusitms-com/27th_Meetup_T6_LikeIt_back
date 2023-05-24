package com.kusitms.hotsixServer.domain.review.dto;

import com.kusitms.hotsixServer.domain.main.dto.res.StickerRes;
import com.kusitms.hotsixServer.domain.place.dto.res.PlaceDetailRes;
import lombok.*;

import java.util.List;


public class ReviewDto {

    @Getter
    @Builder
    public static class reviewRes {
        private Long id;
        private String username;
        private String img;
        private float starRating;
        private String content;
        private int likeCount;
        private int dislikeCount;
        private List<StickerRes> stickers;


        public static reviewRes from(Long id, String name, String img, float starRating, String content, int likeCount, int dislikeCount, List<StickerRes> stickers){
            return reviewRes.builder()
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
    }

    @Getter
    @Builder
    public static class myReviewRes {
        private reviewRes reviewRes;
        private PlaceDetailRes.SimplePlaceRes placeRes;

        public static myReviewRes from(reviewRes reviewRes, PlaceDetailRes.SimplePlaceRes placeInfo) {
            return myReviewRes.builder()
                    .reviewRes(reviewRes)
                    .placeRes(placeInfo)
                    .build();


        }

    }
}
