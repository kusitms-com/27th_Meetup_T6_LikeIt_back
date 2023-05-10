package com.kusitms.hotsixServer.domain.place.service;

import com.kusitms.hotsixServer.domain.place.dto.PlaceDetail;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.global.error.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kusitms.hotsixServer.global.error.ErrorCode.PLACE_ERROR;

@Service
@Transactional
public class PlaceDetailService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public PlaceDetailService(PlaceRepository placeRepository, ReviewRepository reviewRepository) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    public PlaceDetail getPlaceDetail(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow();
        List<Review> reviews = reviewRepository.findByPlaceId(placeId);

        if (place == null) {
            //장소값 존재하지 않을 시 에러 처리
            throw new BaseException(PLACE_ERROR);
        }

        List<ReviewDto> reviewInfos = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDto reviewInfo = ReviewDto.builder()
                    .nickname(review.getUser().getNickname())
                    .starRating(review.getStarRating())
                    .content(review.getContent())
                    .likeCount(review.getLikeCount())
                    .dislikeCount(review.getDislikeCount())
                    .build();
            reviewInfos.add(reviewInfo);
        }

        PlaceDetail.PlaceInfo placeInfo = PlaceDetail.PlaceInfo.builder()
                .id(place.getId())
                .name(place.getName())
                .starRating(place.getStarRating())
                .content(place.getContent())
                .placeImg(place.getPlaceImg())
                .reviewCount(reviews.size())
                //.top2PositiveStickers(place.getTop2PositiveStickers())
                //.top2NegativeStickers(place.getTop2NegativeStickers())
                .reviews(reviewInfos)
                .build();

        return PlaceDetail.builder()
                .places(Collections.singletonList(placeInfo))
                .build();
    }
}
