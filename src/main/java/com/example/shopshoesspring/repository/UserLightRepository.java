package com.example.shopshoesspring.repository;

import com.example.shopshoesspring.entity.UserLight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLightRepository extends JpaRepository<UserLight,Long> {
    List<UserLight> findByUserLightCompletedFalse();
}
