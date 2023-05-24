package com.kusitms.hotsixServer.domain.place.dto;

import com.kusitms.hotsixServer.domain.main.dto.res.StickerRes;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
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
        private char isBookmarked;
        private List<StickerRes> top2stickers;

        public static PlaceInfo from(Place place, List<StickerRes> stickers){
            return PlaceInfo.builder()
                    .id(place.getId())
                    .name(place.getName())
                    .starRating(place.getStarRating())
                    .reviewCount(place.getReviewCount())
                    .placeImg(place.getPlaceImg())
                    .content(place.getContent())
                    .openingHours(place.getOpeningHours())
                    .top2stickers(stickers)
                    .build();
        }


    }
}