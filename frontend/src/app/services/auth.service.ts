import {inject, Injectable} from '@angular/core';
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
    const token = localStorage.getItem("auth_token");
    if (!token) { return false;}
    return !this.isTokenExpired(token);
  }

  isTokenExpired(token: string) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  }

  logout() {
    localStorage.removeItem("auth_token");
    void this.router.navigateByUrl('/');
    //TODO: expand on it
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
      localStorage.removeItem('auth_token');
    }
  }

  setRefreshToken(refreshToken: string | null): void {
    if(refreshToken !== null) {
      localStorage.setItem('refresh_token', refreshToken);
    } else {
      localStorage.removeItem('refresh_token');
    }
  }


}
