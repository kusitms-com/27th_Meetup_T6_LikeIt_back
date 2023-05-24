package com.kusitms.hotsixServer.domain.review.controller;

import com.kusitms.hotsixServer.domain.review.constant.ReviewConstants;
import com.kusitms.hotsixServer.domain.review.dto.req.ReviewReq;
import com.kusitms.hotsixServer.domain.review.service.ReviewService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation("리뷰 작성 코드")
    @PostMapping(value = "/write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> createReview(@RequestPart ReviewReq reviewDto,
                                                    @RequestPart MultipartFile reviewImg) {
        reviewService.createReview(reviewDto, reviewImg);
        return ResponseEntity.ok(ResponseDto.create(
                ReviewConstants.EBoardResponseMessage.REQUEST_REVIEW_SUCCESS.getMessage()));
    }

    @ApiOperation("리뷰 삭제 코드")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ResponseDto> deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ResponseDto.create(
                ReviewConstants.EBoardResponseMessage.DELETE_REVIEW_SUCCESS.getMessage()));
    }

    @ApiOperation("리뷰랑 같아요 클릭 처리 코드")
    @GetMapping(value = "/like/{reviewId}")
    public ResponseEntity<ResponseDto> likeReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.likeReview(reviewId);
        return ResponseEntity.ok(ResponseDto.create(
                ReviewConstants.EBoardResponseMessage.LIKE_REVIEW_SUCCESS.getMessage()));
    }
    @ApiOperation("리뷰랑 달라요 클릭 처리 코드")
    @GetMapping(value = "/dislike/{reviewId}")
    public ResponseEntity<ResponseDto> dislikeReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.dislikeReview(reviewId);
        return ResponseEntity.ok(ResponseDto.create(
                ReviewConstants.EBoardResponseMessage.DISLIKE_REVIEW_SUCCESS.getMessage()));
    }

}
