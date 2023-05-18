package com.kusitms.hotsixServer.domain.main.service;

import com.kusitms.hotsixServer.domain.main.dto.PlaceByBookmarkCntCto;
import com.kusitms.hotsixServer.domain.main.dto.PlaceByCategoryDto;
import com.kusitms.hotsixServer.domain.main.dto.StickerDto;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.review.repository.StickerRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserFilterRepository;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final StickerRepository stickerRepository;

    public List<PlaceByCategoryDto> getPlacesByFilters(){
        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow(); //유저 정보
        List<Place> placeList = placeRepository.findByCategoryInMain(user.getId());
        List<PlaceByCategoryDto> lists = new ArrayList<>();

        for(Place place:placeList){
            List<StickerDto> stickers = stickerRepository.findTop2Stickers(place.getId());
            PlaceByCategoryDto dto = new PlaceByCategoryDto(place.getId(),place.getName(),place.getPlaceImg(),place.getStarRating(),stickers);
            lists.add(dto);
        }

        return lists;

    }

    public PlaceByBookmarkCntCto getTopBookmarks() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = currentDate.with(DayOfWeek.SUNDAY);

        List<PlaceByBookmarkCntCto.Places> restaurants = getPlacesByBookmarkCnt(1L, startOfWeek, endOfWeek);
        List<PlaceByBookmarkCntCto.Places> cafe = getPlacesByBookmarkCnt(2L, startOfWeek, endOfWeek);
        List<PlaceByBookmarkCntCto.Places> plays = getPlacesByBookmarkCnt(3L, startOfWeek, endOfWeek);

        return new PlaceByBookmarkCntCto(restaurants, cafe, plays);
    }

    private List<PlaceByBookmarkCntCto.Places> getPlacesByBookmarkCnt(Long category, LocalDate startOfWeek, LocalDate endOfWeek) {
        List<Place> placeList = placeRepository.findByBookmarkCntInMain(category, startOfWeek.toString(), endOfWeek.toString());
        List<PlaceByBookmarkCntCto.Places> places = new ArrayList<>();

        for (Place place : placeList) {
            PlaceByBookmarkCntCto.Places dto = new PlaceByBookmarkCntCto.Places(place.getId(), place.getName(), place.getPlaceImg());
            places.add(dto);
        }

        return places;
    }

}
