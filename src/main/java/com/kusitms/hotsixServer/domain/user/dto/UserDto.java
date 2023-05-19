package com.kusitms.hotsixServer.domain.user.dto;

import com.kusitms.hotsixServer.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;


public class UserDto implements Serializable {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateInfoReq {

        private  String nickname;

        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대전화 형식에 맞지 않습니다.")
        private  String phoneNum;

        @Pattern(regexp = "^\\d{4}.\\d{2}.\\d{2}$", message = "생년월일 형식에 맞지 않습니다.")
        private  String birthDate;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SocialLoginRes {
        private  Long id;
        private  boolean isSignUp;
        private  String atk;
        private  String rtk;

        public static SocialLoginRes response(Long id, boolean isSignUp, String atk, String rtk) {
            return SocialLoginRes.builder()
                    .id(id)
                    .isSignUp(isSignUp)
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TokenRes {
        private  String atk;
        private  String rtk;

        public static TokenRes response(String atk, String rtk) {
            return TokenRes.builder()
                    .atk(atk)
                    .rtk(rtk)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetUserInfoRes {

        private  String name;

        private  String nickname;

        private  String email;

        private  String phoneNum;

        private  String birthDate;

        private  String imgUrl;

        private  List<String> filters;

        public static GetUserInfoRes response(User user, List<String> filters) {
            return GetUserInfoRes.builder()
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
