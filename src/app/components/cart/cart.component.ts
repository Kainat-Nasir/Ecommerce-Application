import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CartItemService } from 'src/app/services/cart-item.service';
import { Product } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
interface CartItem {
id: number;
  product: Product;  // Represents the product details
  quantity: number;  // Represents the quantity in the cart
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cart: CartItem[] = [];  // Array to hold cart items with product and quantity

  constructor(private cartService: CartItemService, private router: Router) {}

  ngOnInit(): void {
    this.loadCart();
  }

  // Load cart items from backend
  loadCart(): void {
    const userId = localStorage.getItem('userId');  // Get logged-in user ID from localStorage
    if (userId) {
      this.cartService.getUserCart().subscribe(
        response => {
          this.cart = response.cartItems;  // Use the actual 'cartItems' array from the response
        },
        error => {
          console.error('Error fetching cart:', error);
          alert('Please log in to view your cart.');
          this.router.navigate(['/login']);
        }
      );
    } else {
      alert('Please log in to view your cart.');
      this.router.navigate(['/login']);
    }
  }

  // Remove item from cart
  removeFromCart(productId: number): void {
    this.cartService.removeFromCart(productId).subscribe(
      () => {
        alert('Product removed from cart');
        this.loadCart();  // Reload the cart after removing an item
      },
      error => {
        console.error('Error removing product from cart:', error);
        alert('Failed to remove product');
      }
    );
  }

  // Method to handle image URLs (adjust the path)
  getImage(url: string): string {
    if (url) {
      const extractedPath = url.split('assets\\').pop();
      return `assets/${extractedPath}`;
    }
    return 'assets/default-image.jpg';
  }
}
