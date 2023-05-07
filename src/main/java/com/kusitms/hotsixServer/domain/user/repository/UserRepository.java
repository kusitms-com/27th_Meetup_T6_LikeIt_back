package com.kusitms.hotsixServer.domain.user.repository;

import com.kusitms.hotsixServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUserEmail(String email);
    Optional<User> findByUserEmail(String email);
}