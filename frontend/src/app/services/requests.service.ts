import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StreamDetails} from '../pages/home/home.component';
import {RecordingData} from '../pages/watch-recording/watch-recording.component';

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

  getRecordingsByUsername(username: string) {
    return this.http.get<RecordingData[]>(`${this.baseUrl}/recordings/${username}`);
  }

  getRecordingByUsernameAndId(username: string, recordingId: number) {
    return this.http.get<RecordingData>(`${this.baseUrl}/recordings/${username}/${recordingId}`);
  }

  getLiveStreamInfo() {
    return this.http.get<StreamDetails[]>(`${this.baseUrl}/streams`);
  }

  getStreamKey() {
    return this.http.get(`${this.baseUrl}/streams/key`);
  }

}
