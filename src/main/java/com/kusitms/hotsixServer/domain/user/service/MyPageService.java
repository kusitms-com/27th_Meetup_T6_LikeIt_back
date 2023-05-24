package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.main.dto.res.StickerRes;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.place.dto.res.PlaceDetailRes;
import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.domain.review.repository.StickerRepository;
import com.kusitms.hotsixServer.domain.user.dto.req.FilterDtoReq;
import com.kusitms.hotsixServer.domain.user.dto.UserDto;
import com.kusitms.hotsixServer.domain.user.entity.Filter;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import com.kusitms.hotsixServer.domain.user.repository.FilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserFilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import com.kusitms.hotsixServer.global.config.S3UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final S3UploadUtil s3UploadUtil;

    private final UserRepository userRepository;

    private final UserFilterRepository userFilterRepository;

    private final ReviewRepository reviewRepository;

    private final FilterRepository filterRepository;

    private final StickerRepository stickerRepository;

    private final PlaceRepository placeRepository;

    //회원 정보 조회
    public UserDto.GetUserInfoRes getUserInfo() {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<String> filters = userFilterRepository.findAllByUserFetchFilter(user)
                .stream()
                .map(userFilter -> userFilter.getFilter().getName())
                .collect(Collectors.toList());

        return UserDto.GetUserInfoRes.from(user, filters);

    }

    //회원 정보 수정
    public void updateUserInfo(UserDto.UpdateInfoReq updateInfo, MultipartFile multipartFile) {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        if (updateInfo.getNickname() != null) {
            user.updateNickName(updateInfo.getNickname());
        }

        if (updateInfo.getPhoneNum() != null) {
            user.updatePhoneNum(updateInfo.getPhoneNum());
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            user.updateImg(s3UploadUtil.upload(multipartFile, "user"));
        }

        if (updateInfo.getBirthDate() != null) {
            user.updateBirth(updateInfo.getBirthDate());
        }

        userRepository.save(user);
    }

    public List<ReviewDto.myReviewRes> getReviews() {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<Review> reviewList = reviewRepository.findMyReview(user);
        List<ReviewDto.myReviewRes> myReviewResponses = new ArrayList<>();

        for (Review review : reviewList) {
            //리뷰 DTO
            ReviewDto.reviewRes reviewDto = ReviewDto.reviewRes.from(review.getId(), review.getUser().getNickname(), review.getReviewImg(), review.getStarRating(), review.getContent()
                    , review.getLikeCount(), review.getDislikeCount(), StickerInfo(review));
            //장소 DTO
            PlaceDetailRes.SimplePlaceRes placeInfo = new PlaceDetailRes.SimplePlaceRes(review.getPlace().getId(), review.getPlace().getName());

            ReviewDto.myReviewRes myReviewRes = ReviewDto.myReviewRes.from(reviewDto, placeInfo);
            myReviewResponses.add(myReviewRes);
        }

        return myReviewResponses;

    }

    public List<StickerRes> StickerInfo(Review review){
        return stickerRepository.findStickers(review.getId());
    }

    public void updateFilters(FilterDtoReq dto) {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        userFilterRepository.deleteAllByUser(user);
        for (int i = 0; i < dto.getFilters().length; i++) {
            Filter getFilter = filterRepository.findByName(dto.getFilters()[i]).orElseThrow();
            UserFilter userFilter = new UserFilter(getFilter, user);
            userFilterRepository.save(userFilter);
        }

    }

    public List<PlaceDetailRes.SimplePlaceRes2> getBookmark() {
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<Place> placeList = placeRepository.findForBookmark(user.getId());
        List<PlaceDetailRes.SimplePlaceRes2> result = new ArrayList<>();
        for (Place place : placeList) {
            PlaceDetailRes.SimplePlaceRes2 placeInfo = new PlaceDetailRes.SimplePlaceRes2(place.getId(),place.getName(),place.getPlaceImg());
            result.add(placeInfo);
        }

        return result;
    }


}
