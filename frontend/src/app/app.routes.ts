import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {CategoriesComponent} from './pages/categories/categories.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {SettingsComponent} from './pages/settings/settings.component';
import {CategoryContentComponent} from './pages/category-content/category-content.component';
import {AdminComponent} from './pages/admin/admin.component';
import {authGuard} from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'dashboard',
    pathMatch: 'full',
    canActivate: [authGuard],
    component: AdminComponent},
  {path: 'recordings', pathMatch: 'full',
    loadComponent: () =>
      import('./pages/recordings/recordings.component')
        .then(m => m.RecordingsComponent)},
  {path: 'categories', pathMatch: 'full', component: CategoriesComponent},
  {path: 'categories/:categoryUrl', pathMatch: 'full', component: CategoryContentComponent},
  {path: 'history', pathMatch: 'full',
    loadComponent: () =>
      import('./pages/history/history.component')
        .then(m => m.HistoryComponent)},
  {path: 'watch-later', pathMatch: 'full',
    loadComponent: () =>
      import('./pages/watch-later/watch-later.component')
        .then(m => m.WatchLaterComponent)},
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: 'home', pathMatch: 'full', redirectTo: ''},
  {path: 'settings', pathMatch: 'full', component: SettingsComponent},
  {path: 'page-not-found', pathMatch: 'full', component: PageNotFoundComponent},
  {path: ':username',
    loadComponent: () =>
      import('./pages/stream/stream.component')
        .then(m => m.StreamComponent)},
  {path: ':username/recordings',
    loadComponent: () =>
      import('./pages/user-recordings/user-recordings.component')
        .then(m => m.UserRecordingsComponent)},
  {path: ':username/recordings/:recordingId',
    loadComponent: () =>
      import('./pages/watch-recording/watch-recording.component')
        .then(m => m.WatchRecordingComponent)},
  {path: '**', component: PageNotFoundComponent},
];
