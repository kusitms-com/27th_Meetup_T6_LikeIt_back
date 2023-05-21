package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value = "select distinct p.* " +
            "from places p " +
            "         join place_filter pf on p.place_id = pf.place_id " +
            "         join user_filter uf on pf.filter_id = uf.filter_id " +
            "where uf.user_id = :id order by rand() limit 7", nativeQuery = true)
    List<Place> findByFilterInMain(@Param("id") Long id);

    @Query(value = "select p.* from places p " +
            "join bookmarks b on p.place_id = b.place_id\n" +
            "where (p.category1_id=:id) and (b.modified_at between :startOfWeek and :endOfWeek) " +
            "group by b.place_id order by count(b.place_id) desc limit 2;", nativeQuery = true)
    List<Place> findByBookmarkCntInMain(@Param("id") Long id, @Param("startOfWeek") String startOfWeek, @Param("endOfWeek") String endOfWeek);
}
