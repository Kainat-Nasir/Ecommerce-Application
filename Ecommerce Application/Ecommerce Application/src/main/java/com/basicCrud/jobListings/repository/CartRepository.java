package com.basicCrud.jobListings.repository;

import com.basicCrud.jobListings.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//        Optional<Cart> findByProductId( Long productId);

        Optional<Cart> findByUser_Id(Long userId);  // Get cart by user ID
        }
