import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StreamDetails} from '../pages/home/home.component';
import {RecordingData} from '../pages/watch-recording/watch-recording.component';
import {MessageDto, StreamData, StreamTitle} from '../pages/stream/stream.component';

const env = window.__env;

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  private http = inject(HttpClient)

  constructor() { }

  private apiUrl = env.apiUrl;
  private messageDbUrl = env.messageDbUrl;

  getStreamByUsername(username: string) {
    return this.http.get<StreamData>(`${this.apiUrl}/streams/${username}`);
  }

  editStreamTitle(newTitle: StreamTitle) {
    return this.http.patch(`${this.apiUrl}/streams/edit-title`, newTitle);
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

  getAuthTest() {
    return this.http.get(`http://localhost:8082/test`);
  }

  getRecentMessages(chatRoomId: number) {
    return this.http.get<MessageDto[]>(`http://localhost:8083/chat/${chatRoomId}`);
  }

}
