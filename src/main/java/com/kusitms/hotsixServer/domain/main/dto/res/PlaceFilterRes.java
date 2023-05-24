package com.kusitms.hotsixServer.domain.main.dto.res;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceFilterRes {
    private Long id;
    private String name;
    private String img;
    private float rating;
    private List<StickerRes> stickers;
}
