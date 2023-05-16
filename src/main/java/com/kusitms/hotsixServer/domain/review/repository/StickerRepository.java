package com.kusitms.hotsixServer.domain.review.repository;

import com.kusitms.hotsixServer.domain.main.dto.StickerDto;
import com.kusitms.hotsixServer.domain.review.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    Optional<Sticker> findByName(String name);

    @Query(value = "select s.name as sticker, s.is_positive as isPositive from stickers s " +
            "    join review_sticker rs on s.sticker_id = rs.sticker_id " +
            "    join reviews r on r.review_id = rs.review_id " +
            "where r.place_id= :id group by rs.sticker_id order by count(rs.sticker_id) limit 2", nativeQuery = true)
    List<StickerDto> findTop2Stickers(@PathVariable("id") Long id);
}
