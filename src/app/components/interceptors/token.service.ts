import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TokenService implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get the token from localStorage
    const token = localStorage.getItem('authToken'); // Assuming 'authToken' is the key for storing JWT
    // Define the condition for protected URLs (e.g., any request to your backend API)
    const isApiUrl = request.url.startsWith('http://localhost:8080'); 

    // Clone the request and add the Authorization header if the token exists and the URL is protected
    const modifiedRequest = token && isApiUrl ? request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    }) : request;

    // Pass the modified request to the next handler
    return next.handle(modifiedRequest);
  }
}