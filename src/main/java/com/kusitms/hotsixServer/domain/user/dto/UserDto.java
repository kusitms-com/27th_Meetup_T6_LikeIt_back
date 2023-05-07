package com.kusitms.hotsixServer.domain.user.dto;

import lombok.*;

import java.io.Serializable;

public class UserDto implements Serializable {

    @Data
    @Builder
    public static class socialLoginResponse {
        private final Long id;
        private final String atk;
        private final String rtk;

        public static socialLoginResponse response(Long id,  String atk, String rtk) {
            return socialLoginResponse.builder()
                    .id(id)
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }

    }
}
