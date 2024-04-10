package com.example.shopshoesspring.repository;

import com.example.shopshoesspring.entity.Light;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LightRepository extends JpaRepository<Light,Long> {
    List<Light> findByLightTypeId(Long lightTypeId);
}
