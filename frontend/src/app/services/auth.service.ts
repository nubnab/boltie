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

  isLoggedIn(): boolean {
    const token = localStorage.getItem("auth_token");
    if (!token) { return false;}
    return !this.isTokenExpired(token);
  }

  isTokenExpired(token: string): boolean {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  }

  logout(): void {
    localStorage.removeItem("auth_token");
    void this.router.navigateByUrl('/');
    //TODO: expand on it
  }

  setAuthToken(token: string | null): void {
    if(token !== null) {
      window.localStorage.setItem('auth_token', token);
    } else {
      window.localStorage.removeItem('auth_token');
    }
  }

  setRefreshToken(refreshToken: string | null): void {
    if(refreshToken !== null) {
      window.localStorage.setItem('refresh_token', refreshToken);
    } else {
      window.localStorage.removeItem('refresh_token');
    }
  }


}
