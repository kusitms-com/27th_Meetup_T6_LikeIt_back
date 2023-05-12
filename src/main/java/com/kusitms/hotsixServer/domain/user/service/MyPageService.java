package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.review.dto.ReviewDto;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.domain.user.dto.FilterDto;
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

    //회원 정보 조회
    public UserDto.userInfoResponse getUserInfo(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<String> filters = userFilterRepository.findAllByUserFetchFilter(user)
                .stream()
                .map(userFilter -> userFilter.getFilter().getName())
                .collect(Collectors.toList());

        return UserDto.userInfoResponse.response(user, filters);

    }

    //회원 정보 수정
    public void updateUserInfo(UserDto.updateInfo updateInfo, MultipartFile multipartFile){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        if(updateInfo.getNickname()!=null){
            user.updateNickName(updateInfo.getNickname());
        }

        if(updateInfo.getPhoneNum()!=null){
            user.updatePhoneNum(updateInfo.getPhoneNum());
        }

        if(multipartFile != null && !multipartFile.isEmpty()){
            user.updateImg(s3UploadUtil.upload(multipartFile, "user"));
        }

        if(updateInfo.getBirthDate()!=null){
            user.updateBirth(updateInfo.getBirthDate());
        }

        userRepository.save(user);
    }

    public List<ReviewDto.myReviewResponse> getReviews(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        List<Review> reviewList = reviewRepository.findMyReview(user);
        List<ReviewDto.myReviewResponse> myReviewResponses = new ArrayList<>();

        for(Review review : reviewList){
            ReviewDto.myReviewResponse myReviewResponse = ReviewDto.myReviewResponse.response(
                    review.getPlace().getName(),user.getNickname(),review.getStarRating(),review.getContent()
            ,review.getLikeCount(), review.getDislikeCount(), review.getReviewImg());

            myReviewResponses.add(myReviewResponse);
        }

        return myReviewResponses;

    }

    public void updateFilters(FilterDto dto){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보

        userFilterRepository.deleteAllByUser(user);
        for(int i=0; i<dto.getFilters().length; i++){
            Filter getFilter = filterRepository.findByName(dto.getFilters()[i]).orElseThrow();
            UserFilter userFilter = new UserFilter(getFilter, user);
            userFilterRepository.save(userFilter);
        }

    }
}
