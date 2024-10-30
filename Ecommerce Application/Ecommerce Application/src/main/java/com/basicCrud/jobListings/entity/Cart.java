package com.basicCrud.jobListings.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // Prevents serialization of the User in the Cart to avoid recursion
    private UserInfo user;  // Each cart is associated with a single user

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference  // This will serialize the cartItems list when returning a Cart object
    private List<CartItem> cartItems = new ArrayList<>();  // Initialize the list
}
