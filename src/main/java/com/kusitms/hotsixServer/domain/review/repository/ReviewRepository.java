package com.kusitms.hotsixServer.domain.review.repository;

import com.kusitms.hotsixServer.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {



}
