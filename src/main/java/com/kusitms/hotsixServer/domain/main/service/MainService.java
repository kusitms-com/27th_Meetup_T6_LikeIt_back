package com.kusitms.hotsixServer.domain.main.service;

import com.kusitms.hotsixServer.domain.main.dto.res.GetPlaceBookmarkRes;
import com.kusitms.hotsixServer.domain.main.dto.res.GetPlaceFilterRes;
import com.kusitms.hotsixServer.domain.main.dto.res.GetStickerRes;
import com.kusitms.hotsixServer.domain.place.entity.Place;
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

    public List<GetPlaceFilterRes> getPlacesByFilter(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보
        List<Place> placeList = placeRepository.findByFilterInMain(user.getId());
        List<GetPlaceFilterRes> result = new ArrayList<>();

        for(Place place:placeList){
            List<GetStickerRes> stickerList = stickerRepository.findTop2Stickers(place.getId());
            GetPlaceFilterRes dto = new GetPlaceFilterRes(place.getId(),place.getName(),place.getPlaceImg(),place.getStarRating(),stickerList);
            result.add(dto);
        }

        return result;

    }

    public GetPlaceBookmarkRes getTopBookmark() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = currentDate.with(DayOfWeek.SUNDAY);

        List<GetPlaceBookmarkRes.Place> restaurantList = getPlacesByBookmarkCnt(1L, startOfWeek, endOfWeek);
        List<GetPlaceBookmarkRes.Place> cafeList = getPlacesByBookmarkCnt(2L, startOfWeek, endOfWeek);
        List<GetPlaceBookmarkRes.Place> playList = getPlacesByBookmarkCnt(3L, startOfWeek, endOfWeek);

        return new GetPlaceBookmarkRes(restaurantList,cafeList,playList);
    }

    private List<GetPlaceBookmarkRes.Place> getPlacesByBookmarkCnt(Long category, LocalDate startOfWeek, LocalDate endOfWeek) {
        List<Place> placeList = placeRepository.findByBookmarkCntInMain(category, startOfWeek.toString(), endOfWeek.toString());
        List<GetPlaceBookmarkRes.Place> result = new ArrayList<>();

        for (Place place : placeList) {
            GetPlaceBookmarkRes.Place dto = new GetPlaceBookmarkRes.Place(place.getId(), place.getName(), place.getPlaceImg());
            result.add(dto);
        }

        return result;
    }

}
