package com.kusitms.hotsixServer.domain.main.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceByBookmarkCntCto {

    private List<Places> restaurants;
    private List<Places> cafe;
    private List<Places> play;

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    @Builder
    public static class Places {
        private Long id;
        private String name;
        private String img;
    }
}
