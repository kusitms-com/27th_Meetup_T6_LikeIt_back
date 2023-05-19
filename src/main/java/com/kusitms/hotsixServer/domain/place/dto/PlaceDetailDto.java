package com.kusitms.hotsixServer.domain.place.dto;


import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import lombok.*;

import java.util.List;

@Data
@Builder
public class PlaceDetailDto {

    private List<PlaceDetailDto.PlaceInfo> places;

    @Data
    @Builder
    public static class PlaceInfo {
        private Long id;
        private String name;
        private float starRating;
        private String content;
        private String placeImg;
        private int reviewCount;
        private String[] top2PositiveStickers;
        private String[] top2NegativeStickers;
        private int[] top2PositiveStickerCount;
        private int[] top2NegativeStickerCount;
        private char isBookmarked;
        private List<ReviewDto> reviews;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class SimplePlaceInfo {
        private Long id;
        private String name;
    }



}


