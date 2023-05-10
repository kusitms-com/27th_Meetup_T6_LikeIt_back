package com.kusitms.hotsixServer.domain.user.repository;

import com.kusitms.hotsixServer.domain.user.entity.User;
import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFilterRepository extends JpaRepository<UserFilter, Long> {

    @Query("SELECT uf FROM UserFilter uf JOIN FETCH uf.filter WHERE uf.user = :user")
    List<UserFilter> findAllByUserFetchFilter(@Param("user") User user);

    List<UserFilter> findAllByUser(User user);
}
