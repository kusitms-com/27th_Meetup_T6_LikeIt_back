package com.kusitms.hotsixServer.domain.review.repository;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(Long placeId);
    @Query("SELECT rs.sticker.name, COUNT(rs) AS stickerCount FROM ReviewSticker rs WHERE rs.review.place = :place GROUP BY rs.sticker ORDER BY stickerCount DESC")
    List<Object[]> findTopStickersByPlace(@Param("place") Place place, Pageable pageable);
}

