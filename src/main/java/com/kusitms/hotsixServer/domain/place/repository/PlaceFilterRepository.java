package com.kusitms.hotsixServer.domain.place.repository;

import com.kusitms.hotsixServer.domain.place.entity.Place;
import com.kusitms.hotsixServer.domain.place.entity.PlaceFilter;
import com.kusitms.hotsixServer.domain.user.entity.Filter;
import com.kusitms.hotsixServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceFilterRepository extends JpaRepository<PlaceFilter, Long> {
    //사용자 선택 필터값과 제일 관련도 높은 데이터부터 출력
    @Query("SELECT pf.place FROM PlaceFilter pf JOIN pf.filter f JOIN pf.place p JOIN p.category1 c1 WHERE f IN (SELECT uf.filter FROM UserFilter uf WHERE uf.user = :user) AND c1.id = :category1Id GROUP BY pf.place ORDER BY COUNT(pf.place) DESC")
    List<Place> findAllByUserAndCategory1(@Param("user") User user, @Param("category1Id") Long category1Id);

}