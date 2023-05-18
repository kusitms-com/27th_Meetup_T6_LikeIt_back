package com.kusitms.hotsixServer.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestPlaceDto {
    private int orderBy;
    private String[] filters;
    private String category2;

}
