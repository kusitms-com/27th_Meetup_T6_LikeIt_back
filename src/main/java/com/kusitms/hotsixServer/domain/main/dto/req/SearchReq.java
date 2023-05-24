package com.kusitms.hotsixServer.domain.main.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchReq {
    private String word;
    private int orderBy;
    private String[] filters;
    private String category2;
}
