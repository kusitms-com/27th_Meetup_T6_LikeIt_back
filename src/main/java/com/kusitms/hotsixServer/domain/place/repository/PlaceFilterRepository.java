package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.entity.PlaceFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceFilterRepository extends JpaRepository<PlaceFilter, Long> {
    @Query("SELECT pf.place FROM PlaceFilter pf " +
            "JOIN pf.filter f JOIN pf.place p JOIN p.category1 c1 JOIN p.category2 c2 " +
            "WHERE f.name IN :filters AND c1.id = :category1Id " +
            "AND (:category2Id = 0L OR c2.id = :category2Id) " +
            "GROUP BY pf.place " +
            "ORDER BY " +
            "  CASE " +
            "    WHEN :orderBy = 1 THEN p.reviewCount " + // 리뷰 많은 순
            "    WHEN :orderBy = 2 THEN p.createdAt " + // 최신 순
            "    WHEN :orderBy = 3 THEN p.bookmarkCount " + // 북마크 많은 순
            "    WHEN :orderBy = 4 THEN p.starRating " + // 별점 많은 순
            "    ELSE COUNT(pf.place) " +
            "  END DESC")
    List<Place> findAllCategory1AndCategory2(@Param("category1Id") Long category1Id,
                                             @Param("category2Id") Long category2Id,
                                             @Param("orderBy") int orderBy,
                                             @Param("filters") String[] filters);


    @Query("SELECT pf.place FROM PlaceFilter pf " +
            "JOIN pf.filter f JOIN pf.place p JOIN p.category1 c1 JOIN p.category2 c2 " +
            "WHERE f.name IN :filters AND c1.id = :category1Id " +
            "AND (:category2Id = 0L OR c2.id = :category2Id) " +
            "GROUP BY pf.place " +
            "ORDER BY " +
            "p.starRating ASC")
    List<Place> findAllCategory1AndCategory2ASC(@Param("category1Id") Long category1Id,
                                             @Param("category2Id") Long category2Id,
                                             @Param("filters") String[] filters);



    @Query("SELECT pf.place FROM PlaceFilter pf " +
            "JOIN pf.filter f JOIN pf.place p JOIN p.category1 c1 " +
            "WHERE f.name IN :filters AND c1.id = :category1Id " +
            "GROUP BY pf.place " +
            "ORDER BY " +
            "  CASE " +
            "    WHEN :orderBy = 1 THEN p.reviewCount " + // 리뷰 많은 순
            "    WHEN :orderBy = 2 THEN p.createdAt " + // 최신 순
            "    WHEN :orderBy = 3 THEN p.bookmarkCount " + // 북마크 많은 순
            "    WHEN :orderBy = 4 THEN p.starRating " + // 별점 많은 순
            "    ELSE COUNT(pf.place) " +
            "  END DESC")
    List<Place> findAllCategory1(@Param("category1Id") Long category1Id,
                                             @Param("orderBy") int orderBy,
                                             @Param("filters") String[] filters);

    @Query("SELECT pf.place FROM PlaceFilter pf " +
            "JOIN pf.filter f JOIN pf.place p JOIN p.category1 c1 " +
            "WHERE f.name IN :filters AND c1.id = :category1Id " +
            "GROUP BY pf.place " +
            "ORDER BY " +
            "p.starRating ASC")
    List<Place> findAllCategory1ASC(@Param("category1Id") Long category1Id,
                                 @Param("filters") String[] filters);


}