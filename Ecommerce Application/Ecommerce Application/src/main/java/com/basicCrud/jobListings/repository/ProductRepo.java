package com.basicCrud.jobListings.repository;

import com.basicCrud.jobListings.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
    List<Product> findByUserModel_Id(Long userId);
}

