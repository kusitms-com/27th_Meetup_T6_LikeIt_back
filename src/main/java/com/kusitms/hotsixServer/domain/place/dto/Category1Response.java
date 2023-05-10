package com.kusitms.hotsixServer.domain.place.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Category1Response {
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
        private List<String> top2Filters;
    }

    public static Category1Response response(List<PlaceInfo> places) {
        return Category1Response.builder()
                .places(places)
                .build();
    }
}