package com.example.shopshoesspring.repository;

import com.example.shopshoesspring.entity.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct,Long> {
    List<UserProduct> findUserProductByUserIdAndPayedFalse(Long userId);


}
