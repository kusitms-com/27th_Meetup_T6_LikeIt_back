package com.kusitms.hotsixServer.domain.user.repository;

import com.kusitms.hotsixServer.domain.user.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    Optional<Filter> findByName(String name);
}
