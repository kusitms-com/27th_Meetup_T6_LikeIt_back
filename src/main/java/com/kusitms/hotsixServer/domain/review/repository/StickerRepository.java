package com.kusitms.hotsixServer.domain.review.repository;

import com.kusitms.hotsixServer.domain.review.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    Optional<Sticker> findByName(String name);
}
