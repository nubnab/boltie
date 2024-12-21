import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from './services/auth.service';

export const authInterceptor: HttpInterceptorFn = (
  req, next) => {

  const authService = inject(AuthService);
  const token = localStorage.getItem("auth_token");

  if(token && !authService.isTokenExpired(token)) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(req);
}

