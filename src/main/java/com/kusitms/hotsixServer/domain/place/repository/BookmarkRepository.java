package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Bookmark;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByUserAndPlace(User user, Place place);

}
