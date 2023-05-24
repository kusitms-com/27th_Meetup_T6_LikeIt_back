package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.user.entity.User;
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

    @Query(value = "select p.* from places p " +
            "join bookmarks b on p.place_id = b.place_id " +
            "where b.user_id = :id ", nativeQuery = true)
    List<Place> findForBookmark (@Param("id") Long id);

    @Query(value = "SELECT DISTINCT p.* FROM places p " +
            "JOIN place_filter pf ON p.place_id = pf.place_id " +
            "JOIN filters f ON pf.filter = f.filter_id " +
            "WHERE (p.name LIKE %:keyword% OR p.content LIKE %:keyword% OR p.region LIKE %:keyword%) " +
            "AND (:category2Id = 0 OR p.category2_id = :category2Id) " +
            "AND f.name IN :filtersList " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN :orderBy = 1 THEN p.review_count " +
            "       WHEN :orderBy = 2 THEN p.created_at " +
            "       WHEN :orderBy = 3 THEN p.bookmark_count " +
            "       WHEN :orderBy = 4 THEN p.star_rating " +
            "       ELSE p.star_rating " +
            "   END DESC", nativeQuery = true)
    List<Place> findPlacesBySearch(@Param("keyword") String keyword,
                                            @Param("category2Id") Long category2Id,
                                            @Param("filtersList") String[] filtersList,
                                            @Param("orderBy") int orderBy);


}
