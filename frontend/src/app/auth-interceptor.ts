import {HttpInterceptorFn} from '@angular/common/http';
import {catchError, switchMap, throwError} from 'rxjs';
import {inject} from '@angular/core';
import {AuthService} from './services/auth.service';

export const authInterceptor: HttpInterceptorFn = (
  req, next) => {

  const authService = inject(AuthService);
  const token = localStorage.getItem('auth_token')

  if(token !== null && !authService.isTokenExpired(token)) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` }
    });
  }

  return next(req).pipe(
    catchError(err => {
      if(err.status === 401 && authService.getRefreshToken()) {
        return authService.refreshToken().pipe(
          switchMap(res => {
            authService.setAuthToken(res.authToken);

            const clonedRequest = req.clone({
              setHeaders: { Authorization: `Bearer ${res.authToken}` }
            });
            return next(clonedRequest);
          })
        );
      }
      return throwError(() => err);
    })
  );
};
