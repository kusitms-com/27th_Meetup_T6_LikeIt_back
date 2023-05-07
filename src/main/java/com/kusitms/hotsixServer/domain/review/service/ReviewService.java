package com.kusitms.hotsixServer.domain.review.service;

import com.kusitms.hotsixServer.domain.review.dto.RequestReviewDto;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createReview(RequestReviewDto dto) {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();
        Review review = Review.createReview(user, dto);
        reviewRepository.save(review);
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




