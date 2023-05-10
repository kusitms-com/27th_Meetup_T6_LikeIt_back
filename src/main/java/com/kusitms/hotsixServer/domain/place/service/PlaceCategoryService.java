package com.kusitms.hotsixServer.domain.place.service;

import com.kusitms.hotsixServer.domain.place.dto.Category1Response;
import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.entity.PlaceFilter;
import com.kusitms.hotsixServer.domain.place.repository.Category1Repository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceFilterRepository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.global.error.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.hotsixServer.global.error.ErrorCode.CATEGORY1_ERROR;
@Service
@Transactional
@Slf4j
public class PlaceCategoryService {

    private final PlaceRepository placeRepository;
    private final Category1Repository category1Repository;
    private final PlaceFilterRepository placeFilterRepository;

    public PlaceCategoryService(PlaceRepository placeRepository, Category1Repository category1Repository,
                                PlaceFilterRepository placeFilterRepository) {
        this.placeRepository = placeRepository;
        this.category1Repository = category1Repository;
        this.placeFilterRepository = placeFilterRepository;
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
        List<String> topFilterNames = findTop2FilterNamesByPlaceId(place.getId());
        return Category1Response.PlaceInfo.builder()
                .id(place.getId())
                .name(place.getName())
                .starRating(place.getStarRating())
                .reviewCount(place.getReviewCount())
                .placeImg(place.getPlaceImg())
                .content(place.getContent())
                .openingHours(place.getOpeningHours())
                .top2Filters(topFilterNames)
                .build();
    }

    public List<Place> getPlacesByCategory1(Long id) {

        //카테고리 id가 카테고리 범위에 있는지 확인
        Category1 category1 = category1Repository.findById(id)
                .orElseThrow(() -> new BaseException(CATEGORY1_ERROR));

        return placeRepository.findByCategory1(category1);
    }

    private List<String> findTop2FilterNamesByPlaceId(Long placeId) {
        // 상위 필터 2개 name 찾기
        List<PlaceFilter> placeFilters = placeFilterRepository.findByPlaceIdOrderByCountDesc(placeId);
        List<String> topFilterNames = new ArrayList<>();

        int count = 0;
        for (PlaceFilter placeFilter : placeFilters) {
            topFilterNames.add(placeFilter.getFilter().getName());
            count++;
            if (count == 2) {
                break;
            }
        }
        return topFilterNames;
    }
}
