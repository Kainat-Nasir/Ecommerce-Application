package com.basicCrud.jobListings.service;

import com.basicCrud.jobListings.entity.Product;
import com.basicCrud.jobListings.mapper.ProductMapper;
import com.basicCrud.jobListings.model.productDTO;
import com.basicCrud.jobListings.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    @Autowired
    ProductMapper productMapper;
    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductById(int prodId) {
        return repo.findById(prodId).orElse(new Product());
    }


    public Product addProduct(productDTO productModel) {

        Product newProduct = productMapper.addMapper(productModel);

        newProduct.setImageUrl(productModel.getImageUrl());


        return repo.save(newProduct);
    }



    public Product updateProduct(int productId, productDTO productUpdateDto) {
        Product existingProduct = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        Product updatedProduct = productMapper.updateMapper(productUpdateDto, existingProduct);
        return repo.save(updatedProduct);
    }

    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);

    }
    public List<Product> findByUserId(Long id) {
        return repo.findByUserModel_Id(id);
    }
}

