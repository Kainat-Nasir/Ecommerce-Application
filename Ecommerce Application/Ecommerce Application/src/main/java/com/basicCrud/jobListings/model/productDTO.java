package com.basicCrud.jobListings.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class productDTO {

    private int id;
    private String name;
    private String description;
    private Double price;
    private boolean productAvailable;
    private Integer stockQuantity;
    private Long userID;
    private String imageUrl;

}
