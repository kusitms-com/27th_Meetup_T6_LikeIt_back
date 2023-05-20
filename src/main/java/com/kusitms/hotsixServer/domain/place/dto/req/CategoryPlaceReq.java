package com.kusitms.hotsixServer.domain.place.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryPlaceReq { //카테고리별 장소 요청
    private int orderBy;
    private String[] filters;
    private String category2;

}
