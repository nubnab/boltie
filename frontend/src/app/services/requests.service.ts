import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  private http = inject(HttpClient)

  constructor() { }

  private baseUrl = "http://localhost:8080";

  getTest() {
    return this.http.get(`${this.baseUrl}/videos`);
  }

  getStreamByUsername(username: string) {
    return this.http.get(`${this.baseUrl}/streams/${username}`);
  }

  getStreams() {
    return this.http.get(`${this.baseUrl}/streams`);
  }

}
