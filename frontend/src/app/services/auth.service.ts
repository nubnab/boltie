import {computed, inject, Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080";
  private router = inject(Router);
  private http = inject(HttpClient)
  private loginState = signal<boolean>(this.isLoggedIn());

  get loginStateSignal() {
    return this.loginState;
  }

  constructor() { }

  register(username: string, password: string): Observable<any> {
    const url = `${this.baseUrl}/register`;
    const body = { username, password };

    return this.http.post(url, body);
  }

  login(username: string, password: string): Observable<any> {
    const url = `${this.baseUrl}/login`;
    const body = { username, password };

    return this.http.post(url, body);
  }

  isLoggedIn() {
    const token = this.getAuthToken();
    if (!token) { return false;}
    return !this.isTokenExpired(token);
  }

  isTokenExpired(token: string) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  }

  logout() {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("refresh_token");

    this.loginStateSignal.set(false);

    void this.router.navigateByUrl('/');
  }

  getAuthToken(): string | null {
    return localStorage.getItem("auth_token");
  }

  getRefreshToken(): string | null {
    return localStorage.getItem("refresh_token");
  }

  refreshToken() {
    const url = `${this.baseUrl}/refresh`;
    const body = { refresh_token: this.getRefreshToken() };

    return this.http.post(url, body);
  }

  setAuthToken(token: string | null): void {
    if(token !== null) {
      localStorage.setItem('auth_token', token);
    } else {
      this.removeAuthToken();
    }
  }

  setRefreshToken(refreshToken: string | null): void {
    if(refreshToken !== null) {
      localStorage.setItem('refresh_token', refreshToken);
    } else {
      this.removeRefreshToken();
    }
  }

  removeAuthToken(): void {
    localStorage.removeItem("auth_token");
  }

  removeRefreshToken(): void {
    localStorage.removeItem("refresh_token");
  }


}
