import { Routes } from '@angular/router';
import {LoginFormComponent} from './pages/forms/login-form/login-form.component';
import {RegisterFormComponent} from './pages/forms/register-form/register-form.component';

export const routes: Routes = [
  {path: 'login', component: LoginFormComponent},
  {path: 'register', component: RegisterFormComponent},
];
