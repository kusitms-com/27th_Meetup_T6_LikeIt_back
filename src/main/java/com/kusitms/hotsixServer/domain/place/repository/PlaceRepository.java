package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Category1;
import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;


public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("select distinct p from Place p " +
            "join p.placeFilters pf join pf.filter f join f.userFilters uf " +
            "where uf.user = :user " +
            "order by RAND() ")
    List<Place> findByFilterInMain(@Param("user") User user, Pageable pageable);

    @Query("SELECT p FROM Place p JOIN p.bookmarks b " +
            "WHERE (p.category1.id = :id) " +
            "  AND (b.modifiedAt BETWEEN :startOfWeek AND :endOfWeek) " +
            "GROUP BY b.place " +
            "ORDER BY COUNT(b.place) DESC")
    List<Place> findByBookmarkCntInMain(@Param("id") Long id, @Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek, Pageable pageable);

    @Query("select p from Place p join p.bookmarks b where b.user = :user ")
    List<Place> findForBookmark (@Param("user") User user);


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
