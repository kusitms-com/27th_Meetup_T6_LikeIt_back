package com.kusitms.hotsixServer.domain.place.service;

import com.kusitms.hotsixServer.domain.place.dto.Category1Response;
import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.Category1Repository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceFilterRepository;
import com.kusitms.hotsixServer.domain.review.repository.ReviewRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import com.kusitms.hotsixServer.global.error.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;
import static com.kusitms.hotsixServer.global.error.ErrorCode.CATEGORY1_ERROR;
@Service
@Transactional
@Slf4j
public class PlaceCategoryService {

    private final Category1Repository category1Repository;
    private final PlaceFilterRepository placeFilterRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public PlaceCategoryService(Category1Repository category1Repository, PlaceFilterRepository placeFilterRepository,
                                UserRepository userRepository, ReviewRepository reviewRepository) {
        this.category1Repository = category1Repository;
        this.placeFilterRepository = placeFilterRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;

    }

    public Category1Response getPlacesByCategory1Response(Long category1Id) {
        List<Place> places = getPlacesByCategory1(category1Id);
        List<Category1Response.PlaceInfo> placeInfos = new ArrayList<>();

        for (Place place : places) {
            Category1Response.PlaceInfo placeInfo = createPlaceInfo(place);
            placeInfos.add(placeInfo);
        }

        return Category1Response.builder()
                .places(placeInfos)
                .build();
    }

    private Category1Response.PlaceInfo createPlaceInfo(Place place) {
        return Category1Response.PlaceInfo.builder()
                .id(place.getId())
                .name(place.getName())
                .starRating(place.getStarRating())
                .reviewCount(place.getReviewCount())
                .placeImg(place.getPlaceImg())
                .content(place.getContent())
                .openingHours(place.getOpeningHours())
                .top2Stickers(findTop2StickersByPlace(place))
                .build();
    }

    public List<Place> getPlacesByCategory1(Long id) {

        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();

        //카테고리 id가 카테고리 범위에 있는지 확인
        Category1 category1 = category1Repository.findById(id)
                .orElseThrow(() -> new BaseException(CATEGORY1_ERROR));

        return placeFilterRepository.findAllByUserAndCategory1(user, category1.getId());
    }

    private List<String> findTop2StickersByPlace(Place place) {
        List<Object[]> result = reviewRepository.findTopStickersByPlace(place, PageRequest.of(0, 2));
        List<String> topStickers = new ArrayList<>();

        for (Object[] row : result) {
            String stickerName = (String) row[0];
            topStickers.add(stickerName);
        }

        return topStickers;
    }

}
