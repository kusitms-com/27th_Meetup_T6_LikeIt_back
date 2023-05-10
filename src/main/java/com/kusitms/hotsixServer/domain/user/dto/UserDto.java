package com.kusitms.hotsixServer.domain.user.dto;

import com.kusitms.hotsixServer.domain.user.entity.User;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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

    @Data
    @Builder
    public static class tokenResponse {
        private final String atk;
        private final String rtk;

        public static tokenResponse response(String atk, String rtk) {
            return tokenResponse.builder()
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
    }

    @Data
    @Builder
    public static class userInfoResponse {

        private final String name;

        private final String nickname;

        private final String email;

        private final String phoneNum;

        private final String birthDate;

        private final String imgUrl;

        private final List<String> filters;

        public static userInfoResponse response(User user, List<String> filters) {
            return userInfoResponse.builder()
                    .name(user.getUserName())
                    .nickname(user.getNickname())
                    .email(user.getUserEmail())
                    .phoneNum(user.getPhoneNum())
                    .birthDate(user.getBirthDate())
                    .imgUrl(user.getUserImg())
                    .filters(filters)
                    .build();
        }

    }
}
