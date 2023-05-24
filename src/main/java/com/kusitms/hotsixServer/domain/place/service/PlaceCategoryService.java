package com.kusitms.hotsixServer.domain.place.service;

import com.kusitms.hotsixServer.domain.main.dto.res.StickerRes;
import com.kusitms.hotsixServer.domain.place.dto.res.CategoryPlaceRes;
import com.kusitms.hotsixServer.domain.place.dto.req.CategoryPlaceReq;
import com.kusitms.hotsixServer.domain.place.entity.Bookmark;
import com.kusitms.hotsixServer.domain.place.entity.Category2;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.BookmarkRepository;
import com.kusitms.hotsixServer.domain.place.repository.Category2Repository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceFilterRepository;
import com.kusitms.hotsixServer.domain.review.repository.StickerRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import com.kusitms.hotsixServer.domain.user.repository.UserFilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;
@Service
@Transactional
@Slf4j
public class PlaceCategoryService {

    private final Category2Repository category2Repository;
    private final PlaceFilterRepository placeFilterRepository;
    private final UserRepository userRepository;
    private final UserFilterRepository userFilterRepository;
    private final StickerRepository stickerRepository;
    private final BookmarkRepository bookmarkRepository;

    public PlaceCategoryService(Category2Repository category2Repository, PlaceFilterRepository placeFilterRepository, UserRepository userRepository,
                                BookmarkRepository bookmarkRepository, UserFilterRepository userFilterRepository, StickerRepository stickerRepository) {
        this.category2Repository = category2Repository;
        this.placeFilterRepository = placeFilterRepository;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userFilterRepository = userFilterRepository;
        this.stickerRepository = stickerRepository;

    }

    public CategoryPlaceRes getPlacesByCategory1Response(Long Category1Id, CategoryPlaceReq dto) {
        List<Place> places = getPlacesByCategory1(Category1Id, dto); //조건에 맞는 장소 List 출력
        List<CategoryPlaceRes.PlaceInfo> placeInfos = new ArrayList<>();
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();

        for (Place place : places) {
            CategoryPlaceRes.PlaceInfo placeInfo = createPlaceInfo(place, user);
            placeInfos.add(placeInfo);
        }

        return CategoryPlaceRes.builder()
                .places(placeInfos)
                .build();
    }


    public List<Place> getPlacesByCategory1(Long category1Id, CategoryPlaceReq dto) {

        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); // User 조회
        Category2 category2 = category2Repository.findByName(dto.getCategory2()); //카테고리 2 값 조회

        int orderBy = 0;
        if(String.valueOf(dto.getOrderBy())!= null){
            orderBy = dto.getOrderBy();
        } // orderBy값 null이면 0으로 설정

        Long category2Id = 0L;
        if (category2 != null){
            category2Id = category2.getId();
        }

        String[] filters = dto.getFilters();
        if (filters == null || filters.length == 0) {  //선택한 filter값이 null이면 사용자 기본 필터값 가져오기
            List<UserFilter> userFilters = userFilterRepository.findAllByUser(user);

            filters = userFilters.stream()
                    .map(userFilter -> userFilter.getFilter().getName())
                    .toArray(String[]::new);
        }


        if(category1Id==2L){
            if(orderBy == 5){
                return placeFilterRepository.findAllCategory1ASC(category1Id, filters);
            }
            return placeFilterRepository.findAllCategory1(category1Id, orderBy, filters);
        }else if(orderBy == 5){  // orderBy가 5면 별점 오름차순 (별점 낮은순)
            return placeFilterRepository.findAllCategory1AndCategory2ASC(category1Id, category2Id, filters);
        } //나머지는 조건에 따라 내림차순
        return placeFilterRepository.findAllCategory1AndCategory2(category1Id, category2Id, orderBy, filters);
    }


    private CategoryPlaceRes.PlaceInfo createPlaceInfo(Place place, User user) {
        List<StickerRes> stickerList = stickerRepository.findTop2Stickers(place.getId());

        return CategoryPlaceRes.PlaceInfo.builder()
                .id(place.getId())
                .name(place.getName())
                .starRating(place.getStarRating())
                .reviewCount(place.getReviewCount())
                .placeImg(place.getPlaceImg())
                .content(place.getContent())
                .openingHours(place.getOpeningHours())
                .top2stickers(stickerList)
                .isBookmarked(checkBookmark(user, place))
                .build();
    }

    public char checkBookmark(User user, Place place){
        Bookmark bookmark = bookmarkRepository.findByUserAndPlace(user, place);
        if (bookmark == null) {
            return 'N';
        } else {
            return 'Y';
        }
    }

}
