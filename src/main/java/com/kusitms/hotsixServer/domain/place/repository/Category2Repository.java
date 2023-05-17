package com.kusitms.hotsixServer.domain.place.repository;


import com.kusitms.hotsixServer.domain.place.entity.Category2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Category2Repository extends JpaRepository<Category2, Long> {
    Category2 findByName(String name);
}
