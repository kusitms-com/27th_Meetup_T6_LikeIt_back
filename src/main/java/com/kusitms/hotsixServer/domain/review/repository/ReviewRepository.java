package com.kusitms.hotsixServer.domain.review.repository;

import com.kusitms.hotsixServer.domain.review.entity.Review;
import com.kusitms.hotsixServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(Long placeId);

    @Query("SELECT r FROM Review r JOIN FETCH r.place WHERE r.user = :user ORDER BY r.modifiedAt ASC")
    List<Review> findMyReview(@Param("user") User user);

    List<Review> findAllByUserOrderByModifiedAt(User user);
}
