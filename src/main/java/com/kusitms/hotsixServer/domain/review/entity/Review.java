package com.kusitms.hotsixServer.domain.review.entity;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.review.dto.RequestReviewDto;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.Filter;
import com.kusitms.hotsixServer.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Builder
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", referencedColumnName = "place_id")
    private Place place;


    @Column(name = "star_rating")
    private float starRating;

    @Column(name = "content")
    private String content;

    @Column(name = "place_img", columnDefinition = "TEXT", nullable = false)
    private String placeImg;

    @Column(name = "like_count")
    @ColumnDefault("0")
    private int likeCount;

    @Column(name = "dislike_count")
    @ColumnDefault("0")
    private int dislikeCount;

    @Builder
    public static Review createReview (User user, RequestReviewDto dto) {
        return Review.builder()
                .user(user)
                .starRating(dto.getStarRating())
                .content(dto.getContent())
                .placeImg(dto.getPlaceImg())
                .build();
    }
}
