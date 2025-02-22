import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StreamDetails} from '../pages/home/home.component';
import {RecordingData} from '../pages/watch-recording/watch-recording.component';

const env = window.__env;

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  private http = inject(HttpClient)

  constructor() { }

  private apiUrl = env.apiUrl;

  getTest() {
    return this.http.get(`${this.apiUrl}/videos`);
  }

  getStreamByUsername(username: string) {
    return this.http.get(`${this.apiUrl}/streams/${username}`);
  }

  getRecordingsByUsername(username: string) {
    return this.http.get<RecordingData[]>(`${this.apiUrl}/recordings/${username}`);
  }

  getRecordingByUsernameAndId(username: string, recordingId: number) {
    return this.http.get<RecordingData>(`${this.apiUrl}/recordings/${username}/${recordingId}`);
  }

  getLiveStreamInfo() {
    return this.http.get<StreamDetails[]>(`${this.apiUrl}/streams`);
  }

  getStreamKey() {
    return this.http.get(`${this.apiUrl}/streams/key`);
  }

}
