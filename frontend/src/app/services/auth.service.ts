import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080";
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

  setAuthToken(token: string | null): void {
    if(token !== null) {
      window.localStorage.setItem('auth_token', token);
    } else {
      window.localStorage.removeItem('auth_token');
    }
  }


}
