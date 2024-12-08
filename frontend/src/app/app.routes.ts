import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LiveComponent} from './pages/live/live.component';
import {VideosComponent} from './pages/videos/videos.component';
import {CategoriesComponent} from './pages/categories/categories.component';
import {HistoryComponent} from './pages/history/history.component';
import {WatchLaterComponent} from './pages/watch-later/watch-later.component';
import {AboutComponent} from './pages/about/about.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {LoginFormComponent} from './forms/login-form/login-form.component';
import {RegisterFormComponent} from './forms/register-form/register-form.component';

export const routes: Routes = [
  {path: 'live', pathMatch: 'full', component: LiveComponent},
  {path: 'videos', pathMatch: 'full', component: VideosComponent},
  {path: 'categories', pathMatch: 'full', component: CategoriesComponent},
  {path: 'history', pathMatch: 'full', component: HistoryComponent},
  {path: 'watch-later', pathMatch: 'full', component: WatchLaterComponent},
  {path: 'about', pathMatch: 'full', component: AboutComponent},
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: 'login', pathMatch: 'full', component: LoginFormComponent},
  {path: 'register', pathMatch: 'full', component: RegisterFormComponent},
  {path: 'home', pathMatch: 'full', redirectTo: ''},
  {path: '**', component: PageNotFoundComponent},
];
