package com.kusitms.hotsixServer.domain.main.dto.res;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceBookmarkRes {

    private List<Place> restaurant;
    private List<Place> cafe;
    private List<Place> play;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Place {
        private Long id;
        private String name;
        private String img;
    }
}
