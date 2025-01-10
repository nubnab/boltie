import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LiveComponent} from './pages/live/live.component';
import {VideosComponent} from './pages/videos/videos.component';
import {CategoriesComponent} from './pages/categories/categories.component';
import {HistoryComponent} from './pages/history/history.component';
import {WatchLaterComponent} from './pages/watch-later/watch-later.component';
import {AboutComponent} from './pages/about/about.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {StreamComponent} from './pages/stream/stream.component';
import {SettingsComponent} from './pages/settings/settings.component';
import {UserRecordingsComponent} from './pages/user-recordings/user-recordings.component';
import {WatchRecordingComponent} from './pages/watch-recording/watch-recording.component';

export const routes: Routes = [
  {path: 'live', pathMatch: 'full', component: LiveComponent},
  {path: 'videos', pathMatch: 'full', component: VideosComponent},
  {path: 'categories', pathMatch: 'full', component: CategoriesComponent},
  {path: 'history', pathMatch: 'full', component: HistoryComponent},
  {path: 'watch-later', pathMatch: 'full', component: WatchLaterComponent},
  {path: 'about', pathMatch: 'full', component: AboutComponent},
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: 'home', pathMatch: 'full', redirectTo: ''},
  {path: 'settings', pathMatch: 'full', component: SettingsComponent},
  {path: 'page-not-found', pathMatch: 'full', component: PageNotFoundComponent},
  {path: ':username', component: StreamComponent},
  {path: ':username/recordings', component: UserRecordingsComponent},
  {path: ':username/recordings/:recordingId', component: WatchRecordingComponent},
  {path: '**', component: PageNotFoundComponent},
];
