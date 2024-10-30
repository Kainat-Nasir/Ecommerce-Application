package com.basicCrud.jobListings.service;

import com.basicCrud.jobListings.entity.Cart;
import com.basicCrud.jobListings.entity.CartItem;
import com.basicCrud.jobListings.entity.Product;
import com.basicCrud.jobListings.repository.CartItemRepository;
import com.basicCrud.jobListings.repository.CartRepository;
import com.basicCrud.jobListings.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart addProductToCart(Long userId, int productId, int quantity) {
        // Fetch or create a new cart for the user
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        cart.setUser(userInfoService.findById(userId));

        // Ensure cart items are initialized
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        // Look for the existing product in the cart
        cart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), productId))
                .findFirst()
                .ifPresentOrElse(
                        // If product exists, update the quantity
                        existingItem -> existingItem.setQuantity(existingItem.getQuantity() + quantity),
                        // If product doesn't exist, add it as a new CartItem
                        () -> {
                            Product product = productRepo.findById((int) productId).orElseThrow(() ->
                                    new IllegalArgumentException("Product not found with id: " + productId)
                            );
                            CartItem newItem = new CartItem();
                            newItem.setProduct(product);
                            newItem.setQuantity(quantity);
                            newItem.setCart(cart);
                            cart.getCartItems().add(newItem);
                        }
                );

        // Save the cart
        return cartRepository.save(cart);
    }


    // Method to get a cart by user ID
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user with ID: " + userId));
    }
    public boolean removeFromCart(Long cartId) {
        // Find the cart item by its ID
        Optional<Cart> cartItemOptional = cartRepository.findById(cartId);

        // Check if the cart item exists
        if (cartItemOptional.isPresent()) {
            cartRepository.deleteById(cartId); // Remove the cart item
            return true; // Successfully removed
        } else {
            return false; // Cart item not found
        }
    }
    // Method to get all carts for admin view
    @PreAuthorize("hasAuthority('Admin')")
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
