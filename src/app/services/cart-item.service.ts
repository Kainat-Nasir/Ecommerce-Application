import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product.service';

interface CartItem {
  product: Product;
  quantity: number;
}
@Injectable({
  providedIn: 'root'
})
export class CartItemService {
  private apiUrl = 'http://localhost:8080/cart';  // Base URL for cart APIs

  constructor(private http: HttpClient) {}

  addProductToCart(productId: number, quantity: number): Observable<any> {
    // Construct the URL with query parameters
    const url = `${this.apiUrl}/add?productId=${productId}&quantity=${quantity}`;
  
    // Send a POST request with an empty body (or you can also pass {})
    return this.http.post(url, {});
  }
  getUserCart(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/all-items`);  // No need to pass userId
  }
  // Remove product from cart

  // Method to remove an item from the cart using cartId
  removeFromCart(productId: number): Observable<void> {
    const userId = this.getUserId(); // Assuming you fetch the logged-in user's ID
    return this.http.delete<void>(`${this.apiUrl}/remove/${userId}/${productId}`);
  }

  private getUserId(): number {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('User ID not found in localStorage');
    }
    return parseInt(userId, 10);  // Convert the userId from string to number
  }
}
