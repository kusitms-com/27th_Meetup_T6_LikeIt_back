package com.kusitms.hotsixServer.domain.review.service;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.review.dto.RequestReviewDto;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.review.entity.ReviewSticker;
import com.kusitms.hotsixServer.domain.review.entity.Sticker;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.domain.review.repository.ReviewStickerRepository;
import com.kusitms.hotsixServer.domain.review.repository.StickerRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import com.kusitms.hotsixServer.global.config.S3UploadUtil;
import com.kusitms.hotsixServer.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;
import static com.kusitms.hotsixServer.global.error.ErrorCode.STICKER_ERROR;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final StickerRepository stickerRepository;
    private final ReviewStickerRepository reviewStickerRepository;

    private final S3UploadUtil s3UploadUtil;

    @Transactional
    public void createReview(RequestReviewDto dto, MultipartFile reviewImg) {

        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();
        Place place = placeRepository.findById(dto.getPlaceId()).orElseThrow();
        String imgPath = s3UploadUtil.upload(reviewImg, "review");
        Review review = Review.createReview(user, place, imgPath, dto);

        reviewRepository.save(review);

        String[] stickers = dto.getStickers();
        for (String stickerName : stickers) {
            Sticker sticker = stickerRepository.findByName(stickerName).orElseThrow(() ->
                    new BaseException(STICKER_ERROR));

            ReviewSticker reviewSticker = new ReviewSticker(review, sticker, sticker.getIsPositive());
            reviewStickerRepository.save(reviewSticker);
        }
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.delete(review);
        }
    }

}




