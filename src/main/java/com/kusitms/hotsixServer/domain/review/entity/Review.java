package com.kusitms.hotsixServer.domain.review.entity;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.review.dto.RequestReviewDto;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Entity
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

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

    @Column(name = "review_img", columnDefinition = "TEXT", nullable = false)
    private String reviewImg;

    @Column(name = "like_count", columnDefinition = "int default 0")
    private int likeCount;

    @Column(name = "dislike_count", columnDefinition = "int default 0")
    private int dislikeCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewSticker> reviewStickers = new ArrayList<>();
    
    @Builder
    public static Review createReview (User user, Place place, String imgPath, RequestReviewDto dto) {
        return Review.builder()
                .user(user)
                .place(place)
                .starRating(dto.getStarRating())
                .content(dto.getContent())
                .reviewImg(imgPath)
                .build();
    }
}
