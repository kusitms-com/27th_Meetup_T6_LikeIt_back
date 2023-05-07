package com.kusitms.hotsixServer.domain.review.controller;

import com.kusitms.hotsixServer.domain.review.constant.ReviewConstants;
import com.kusitms.hotsixServer.domain.review.dto.RequestReviewDto;
import com.kusitms.hotsixServer.domain.review.service.ReviewService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(("/create"))
    public ResponseEntity<ResponseDto> createReview(@RequestBody RequestReviewDto reviewDto) {
            this.reviewService.createReview(reviewDto);
            return ResponseEntity.ok(ResponseDto.create(
                    ReviewConstants.EBoardResponseMessage.REQUEST_REVIEW_SUCCESS.getMessage()));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ResponseDto> deleteReview(@PathVariable("reviewId") Long reviewId) {
        this.reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ResponseDto.create(
                ReviewConstants.EBoardResponseMessage.DELETE_REVIEW_SUCCESS.getMessage()));
    }

}
