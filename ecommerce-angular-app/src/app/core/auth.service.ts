import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../shared/models/user.model';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import {jwtDecode } from 'jwt-decode';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private baseUrl = 'http://localhost:9090/api/auth';
  private loggedInUsername = new BehaviorSubject<string | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    const username = this.getUsernameFromToken();
    if (username) {
      this.setLoggedInUsername(username);
    }
  }

  getUsernameFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    try {
      const decoded: any = jwtDecode(token);
      return decoded.sub || decoded.username;  // Depends on backend
    } catch (e) {
      console.error('Invalid token');
      return null;
    }
  }

  setLoggedInUsername(username: string) {
    this.loggedInUsername.next(username);
  }

  getLoggedInUsername(): Observable<string | null> {
    return this.loggedInUsername.asObservable();
  }

  isTokenExpired(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return true;

    try {
      const decodedToken: any = jwtDecode(token);
      const expiryTime = decodedToken.exp;
      const now = Math.floor(Date.now() / 1000);
      return expiryTime < now;
    } catch (err) {
      console.error('Invalid token:', err);
      return true;
    }
  }
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          // Optionally decode the token to extract user info
          const decodedToken = jwtDecode(response.token);
       //   this.loggedInUsername.next(decodedToken); // or create a User object manually
        }
      })
    );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !this.isTokenExpired();
  }

  getUserIdFromToken(): number {
    const token = localStorage.getItem('token');
    if (!token) {return 0;}
    else {
    const decoded: any = jwtDecode(token);
    return decoded?.userId;
    }
  }
}
