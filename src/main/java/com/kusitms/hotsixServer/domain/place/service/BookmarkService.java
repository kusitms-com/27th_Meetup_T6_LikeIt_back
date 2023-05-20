package com.kusitms.hotsixServer.domain.place.service;

import com.kusitms.hotsixServer.domain.place.constant.PlaceConstants;
import com.kusitms.hotsixServer.domain.place.entity.Bookmark;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.repository.BookmarkRepository;
import com.kusitms.hotsixServer.domain.place.repository.PlaceRepository;
import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms.hotsixServer.global.config.SecurityUtil.getCurrentUserEmail;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public String setBookmark(Long placeId) {

        User user = userRepository.findByUserEmail(getCurrentUserEmail()).orElseThrow();
        Place place = placeRepository.findById(placeId).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findByUserAndPlace(user, place);

        if (bookmark == null) {
            bookmark = new Bookmark(user, place);
            bookmarkRepository.save(bookmark);
            place.setBookmarkCount(place.getBookmarkCount() + 1);
            return PlaceConstants.EBoardResponseMessage.RESPONSE_ADD_BOOKMARK_SUCCESS.getMessage();
        } else {
            bookmarkRepository.delete(bookmark);
            place.setBookmarkCount(place.getBookmarkCount() - 1);
            return PlaceConstants.EBoardResponseMessage.RESPONSE_DELETE_BOOKMARK_SUCCESS.getMessage();
        }
    }
}
