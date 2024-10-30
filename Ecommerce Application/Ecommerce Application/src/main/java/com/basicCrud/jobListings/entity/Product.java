package com.basicCrud.jobListings.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Product_Details")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Double price;
    private boolean productAvailable=true;
    private Integer stockQuantity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userModel;
    private String imageUrl;


}
