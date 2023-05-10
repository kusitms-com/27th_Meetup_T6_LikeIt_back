package com.kusitms.hotsixServer.domain.place.dto;


import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceDetail {

    private List<PlaceDetail.PlaceInfo> places;

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
        private List<ReviewDto> reviews;
    }

}


