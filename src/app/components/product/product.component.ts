import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartItemService } from 'src/app/services/cart-item.service';
import { Product, ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
   
  products: Product[] = [];
  cartItemCount: number = 0;

  constructor(
    private cartservice: CartItemService, 
    private productService: ProductService, 
    private userService: UserService, 
    private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadCartCount();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: Product[]) => {
        this.products = data.map(product => ({
          ...product, selectedQuantity: 1 // Initialize selected quantity to 1
        }));
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );
  }
// Function to increase quantity of a product
increaseQuantity(product: Product): void {
  if (product.selectedQuantity < product.stockQuantity) {
    product.selectedQuantity += 1;
  }
}

// Function to decrease quantity of a product
decreaseQuantity(product: Product): void {
  if (product.selectedQuantity > 1) {
    product.selectedQuantity -= 1;
  }
}
  getImage(url: any){
    if(url){
      return url.replace(/^.*[\\\/]src[\\\/]assets[\\\/]/, 'assets/');
    }
    return "";
  }

  loadCartCount(): void {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    this.cartItemCount = cart.length;
  }

  addToCart(product: Product): void {
    const quantity = 1;
    this.cartservice.addProductToCart(product.id, quantity).subscribe(
      response => {
        alert('Product added to cart');
      },
      error => {
        console.error('Error adding product to cart:', error);
        alert('Failed to add product to cart');
      }
    );
  }

   // Check if the user is logged in
   isLoggedIn(): boolean {
    return this.userService.isLoggedIn(); // Implement this in UserService
  }

  // Handle user logout
  logout(): void {
    this.userService.logout(); // Implement logout functionality in UserService
    this.router.navigate(['/login']);
  }

}