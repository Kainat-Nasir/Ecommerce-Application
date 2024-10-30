import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


export interface Product {
  selectedQuantity: number;
  id: number;
  name: string;
  description: string;
  price: number;
  productAvailable: boolean;
  stockQuantity: number;
  imageUrl: string;
}
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/all`);
  }

  addProduct(productData: FormData): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/add`, productData);
  }
  addProductToCart(productId: number, quantity: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/cart/add`, { productId, quantity });
  }
  updateProduct(productId: number, product: FormData): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/update/${productId}`, product);
  }

  deleteProduct(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${productId}`);


}
}