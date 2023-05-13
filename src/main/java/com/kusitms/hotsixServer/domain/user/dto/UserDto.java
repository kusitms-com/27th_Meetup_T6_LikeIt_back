package com.kusitms.hotsixServer.domain.user.dto;

import com.kusitms.hotsixServer.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    @Builder
    public static class updateInfo {

        private final String nickname;

        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대전화 형식에 맞지 않습니다.")
        private final String phoneNum;

        @Pattern(regexp = "^\\d{4}.\\d{2}.\\d{2}$", message = "생년월일 형식에 맞지 않습니다.")
        private final String birthDate;

    }

    @Data
    @Builder
    public static class socialLoginResponse {
        private final Long id;
        private final boolean isSignUp;
        private final String atk;
        private final String rtk;

        public static socialLoginResponse response(Long id, boolean isSignUp, String atk, String rtk) {
            return socialLoginResponse.builder()
                    .id(id)
                    .isSignUp(isSignUp)
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
