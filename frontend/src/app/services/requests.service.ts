import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  constructor(private http: HttpClient) { }

  private baseUrl = "http://localhost:8080";

  getTest() {
    return this.http.get(`${this.baseUrl}/test`);
  }

}
