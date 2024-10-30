package com.basicCrud.jobListings.Controller;

import com.basicCrud.jobListings.entity.Cart;
import com.basicCrud.jobListings.service.CartService;
import com.basicCrud.jobListings.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestParam int productId,
                                              @RequestParam int quantity) {
        Long userId = SecurityUtil.getCurrentUserId();  // Get the logged-in user ID
        Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);

        return ResponseEntity.ok(updatedCart);
    }

    //  To get user's cart
    @GetMapping("/all-items")
    public ResponseEntity<Cart> getUserCart() {
        Long userId = SecurityUtil.getCurrentUserId();
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(userCart);
    }

    // Endpoint for admin to view all carts
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
    // Remove product from cart
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId) {
        boolean removed = cartService.removeFromCart(cartId);
        if (removed) {
            return new ResponseEntity<>("Product removed from cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove product", HttpStatus.NOT_FOUND);
        }
    }
}
