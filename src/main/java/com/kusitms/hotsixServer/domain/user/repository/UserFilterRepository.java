package com.kusitms.hotsixServer.domain.user.repository;

import com.kusitms.hotsixServer.domain.user.entity.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFilterRepository extends JpaRepository<UserFilter, Long> {

}
