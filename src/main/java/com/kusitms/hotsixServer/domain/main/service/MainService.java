package com.kusitms.hotsixServer.domain.main.service;

import com.kusitms.hotsixServer.domain.main.dto.req.SearchReq;
import com.kusitms.hotsixServer.domain.main.dto.res.PlaceBookmarkRes;
import com.kusitms.hotsixServer.domain.main.dto.res.PlaceFilterRes;
import com.kusitms.hotsixServer.domain.main.dto.res.StickerRes;
import com.kusitms.hotsixServer.domain.place.dto.res.CategoryPlaceRes;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.Category2Repository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.review.repository.StickerRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final StickerRepository stickerRepository;
    private final Category2Repository category2Repository;

    public List<PlaceFilterRes> getPlacesByFilter(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보
        List<Place> placeList = placeRepository.findByFilterInMain(user.getId());
        List<PlaceFilterRes> result = new ArrayList<>();

        for(Place place:placeList){
            List<StickerRes> stickerList = stickerRepository.findTop2Stickers(place.getId());
            PlaceFilterRes dto = new PlaceFilterRes(place.getId(),place.getName(),place.getPlaceImg(),place.getStarRating(),stickerList);
            result.add(dto);
        }

        return result;

    }

    public PlaceBookmarkRes getTopBookmark() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = currentDate.with(DayOfWeek.SUNDAY);

        List<PlaceBookmarkRes.Place> restaurantList = getPlacesByBookmarkCnt(1L, startOfWeek, endOfWeek);
        List<PlaceBookmarkRes.Place> cafeList = getPlacesByBookmarkCnt(2L, startOfWeek, endOfWeek);
        List<PlaceBookmarkRes.Place> playList = getPlacesByBookmarkCnt(3L, startOfWeek, endOfWeek);

        return new PlaceBookmarkRes(restaurantList,cafeList,playList);
    }

    private List<PlaceBookmarkRes.Place> getPlacesByBookmarkCnt(Long category, LocalDate startOfWeek, LocalDate endOfWeek) {
        List<Place> placeList = placeRepository.findByBookmarkCntInMain(category, startOfWeek.toString(), endOfWeek.toString());
        List<PlaceBookmarkRes.Place> result = new ArrayList<>();

        for (Place place : placeList) {
            PlaceBookmarkRes.Place dto = new PlaceBookmarkRes.Place(place.getId(), place.getName(), place.getPlaceImg());
            result.add(dto);
        }

        return result;
    }

    public List<CategoryPlaceRes.PlaceInfo> getPlacesBySearch(SearchReq req) {
        Long category2Id = req.getCategory2() != null ? category2Repository.findByName(req.getCategory2()).getId() : 0L;

        String[] filters = req.getFilters();
        if (filters == null) {
            filters = new String[]{"조용한", "색다른", "전통적인", "모던한", "화려한", "로맨틱한", "활기찬", "트렌디한"};
        }

        List<Place> placeList = placeRepository.findPlacesBySearch(req.getWord(),category2Id, filters, req.getOrderBy());
        log.info(placeList.size()+" ");
        List<CategoryPlaceRes.PlaceInfo> result = new ArrayList<>();
        for (Place place : placeList) {
            List<StickerRes> stickerList = stickerRepository.findTop2Stickers(place.getId());
            CategoryPlaceRes.PlaceInfo placeInfo = CategoryPlaceRes.PlaceInfo.from(place, stickerList);
            result.add(placeInfo);
        }

        return result;
    }

}
