package com.kusitms.hotsixServer.domain.review.dto;

import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

    private String nickname;
    private float starRating;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private String[] Stickers;

    @Data
    @Builder
    public static class myReviewResponse {
        private String placeName;
        private String nickname;
        private float starRating;
        private String content;
        private int likeCount;
        private int dislikeCount;
        private String img;

        public static myReviewResponse response(
                String placeName,String nickname,float starRating,String content,
                int likeCount,int dislikeCount,String img) {
            return myReviewResponse.builder()
                    .placeName(placeName)
                    .nickname(nickname)
                    .starRating(starRating)
                    .content(content)
                    .likeCount(likeCount)
                    .dislikeCount(dislikeCount)
                    .img(img).build();
        }

    }

}
