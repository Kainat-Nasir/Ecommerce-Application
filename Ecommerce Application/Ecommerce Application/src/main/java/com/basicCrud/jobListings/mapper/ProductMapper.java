package com.basicCrud.jobListings.mapper;

import com.basicCrud.jobListings.entity.Product;
import com.basicCrud.jobListings.model.productDTO;
import com.basicCrud.jobListings.service.UserInfoService;
import com.basicCrud.jobListings.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ProductMapper {
    @Autowired
    UserInfoService userService;

    public Product addMapper(productDTO productDTO) {
        Product mapProduct = new Product();
        mapProduct.setName(productDTO.getName());
        mapProduct.setDescription(productDTO.getDescription());
        mapProduct.setPrice(productDTO.getPrice());
        mapProduct.setStockQuantity(productDTO.getStockQuantity());
        mapProduct.setProductAvailable(productDTO.isProductAvailable());
        mapProduct.setImageUrl(productDTO.getImageUrl());

        mapProduct.setUserModel(userService.findById(SecurityUtil.getCurrentUserId()));

        return mapProduct;
    }
//    public Product updateMapper(productDTO productUpdateDto, Product existingProduct) {
//        existingProduct.setUserModel(userService.findById(SecurityUtil.getCurrentUserId()));
//        if (productUpdateDto.getName() != null) {
//            existingProduct.setName(productUpdateDto.getName());
//        }
//        if (productUpdateDto.getDescription() != null) {
//            existingProduct.setDescription(productUpdateDto.getDescription());
//        }
//        if (productUpdateDto.getPrice() != null) {
//            existingProduct.setPrice(productUpdateDto.getPrice());
//        }
//        if (productUpdateDto.isProductAvailable() != existingProduct.isProductAvailable()) {
//            existingProduct.setProductAvailable(productUpdateDto.isProductAvailable());
//        }
//        if (productUpdateDto.getStockQuantity() > 0) {
//            existingProduct.setStockQuantity(productUpdateDto.getStockQuantity());
//        }
//
//
//        return existingProduct;
//    }
public Product updateMapper(productDTO productUpdateDTO, Product existingProduct) {
    if (existingProduct != null) {
        existingProduct.setName(productUpdateDTO.getName());
        existingProduct.setDescription(productUpdateDTO.getDescription());
        existingProduct.setPrice(productUpdateDTO.getPrice());
        existingProduct.setStockQuantity(productUpdateDTO.getStockQuantity());
        existingProduct.setProductAvailable(productUpdateDTO.isProductAvailable());
        existingProduct.setImageUrl(productUpdateDTO.getImageUrl());

        // Set the user model based on the current user ID
        existingProduct.setUserModel(userService.findById(SecurityUtil.getCurrentUserId()));
    }
    return existingProduct;
}


}


