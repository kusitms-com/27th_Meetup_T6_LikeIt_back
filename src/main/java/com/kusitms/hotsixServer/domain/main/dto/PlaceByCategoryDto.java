package com.kusitms.hotsixServer.domain.main.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceByCategoryDto {
    private Long id;
    private String name;
    private String img;
    private float rating;
    private List<StickerDto> stickers;
}
