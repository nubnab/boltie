import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StreamDetails} from '../pages/home/home.component';
import {RecordingData} from '../pages/watch-recording/watch-recording.component';
import {MessageDto, StreamData, StreamTitle} from '../pages/stream/stream.component';
import {Category} from '../pages/categories/categories.component';
import {Role, UserRoles} from '../pages/admin/admin.component';

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

  editStreamCategory(newCategory: number) {
    return this.http.patch(`${this.apiUrl}/streams/edit-category`, newCategory);
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

  getRecentMessages(chatRoomId: number) {
    return this.http.get<MessageDto[]>(`${this.messageDbUrl}/chat/${chatRoomId}`);
  }

  getWatchHistory() {
    return this.http.get<RecordingData[]>(`${this.apiUrl}/history`);
  }

  addToWatchLater(username: string, userRecordingTrackingId: number) {
    return this.http.post(`${this.apiUrl}/watch-later/${username}/${userRecordingTrackingId}`, {})
  }

  getWatchLater() {
    return this.http.get<RecordingData[]>(`${this.apiUrl}/watch-later`);
  }

  getCategories() {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }

  getLiveStreamsFromCategory(categoryUrl: string) {
    return this.http.get<StreamDetails[]>(`${this.apiUrl}/streams/category/${categoryUrl}`);
  }

  getCategoryContent(categoryUrl: string) {
    return this.http.get<RecordingData[]>(`${this.apiUrl}/categories/${categoryUrl}`);
  }

  getUserRoles() {
    return this.http.get<UserRoles[]>(`${this.apiUrl}/users`);
  }

  changeUserRole(id: number, role: Role) {
    return this.http.patch(`${this.apiUrl}/users/${id}`, role);
  }

}
