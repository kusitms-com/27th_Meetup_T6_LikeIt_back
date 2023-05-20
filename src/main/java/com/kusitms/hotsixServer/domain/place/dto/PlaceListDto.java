package com.kusitms.hotsixServer.domain.place.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PlaceListDto {
    private List<PlaceInfo> places;

    @Data
    @Builder
    public static class PlaceInfo {
        private Long id;
        private String name;
        private float starRating;
        private int reviewCount;
        private String placeImg;
        private String content;
        private String openingHours;
        private String[] top2Stickers;
        private char isBookmarked;
    }
}