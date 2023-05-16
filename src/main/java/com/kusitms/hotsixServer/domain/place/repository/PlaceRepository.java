package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.main.dto.PlaceByCategoryDto;
import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByCategory1(Category1 category1);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM places p " +
            "         JOIN place_filter pf ON p.place_id = pf.place_id " +
            "         JOIN user_filter uf ON pf.filter_id = uf.filter_id " +
            "WHERE uf.user_id = :id ORDER BY rand() limit 7", nativeQuery = true)
    List<Place> findByCategoryInMain(@Param("id") Long id);
}
