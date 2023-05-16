package com.kusitms.hotsixServer.domain.main.service;

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
    private final UserFilterRepository userFilterRepository;
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

}
