import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface User {
  id: number;
  name: string;
  phone: string;
  email: string;
  roles: string;  

  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/user';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(user => {
        this.currentUserSubject.next(user);
        if (user.token) {
          localStorage.setItem('authToken', user.token);
          localStorage.setItem('userId', user.id.toString());
          localStorage.setItem('role', user.roles);
        }
      })
    );
  }
  isAdmin(): boolean {
    return localStorage.getItem('role') === 'Admin';
  }
  register(username: string, email: string, password: string): Observable<string> {
    const userData = { username, email, password };
    return this.http.post(`${this.apiUrl}/register`, userData, { responseType: 'text' });
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken'); // This checks if 'authToken' exists in localStorage
  }

  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('authToken');
   
  }
  setCurrentUser(user: any) {
    this.currentUserSubject.next(user);
  }
}
