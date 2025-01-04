import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from './services/auth.service';
import {catchError, of, switchMap} from 'rxjs';

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

  return next(req).pipe(
    catchError((error) => {
      if(error.status == 401) {
        const refreshToken = localStorage.getItem('refresh_token');
        if(refreshToken) {
          return authService.refreshToken().pipe(
            switchMap((res: any) => {

              const newAuthToken = res.authToken;

              localStorage.setItem('auth_token', newAuthToken);

              const cloneRequest = req.clone({
                setHeaders: {
                  Authorization: `Bearer ${newAuthToken}`,
                },
              });
              return next(cloneRequest);
            }),
            catchError((refreshError) => {
              authService.logout();
              return of(refreshError);
            })
          );
        } else {
          authService.logout();
          return of(error);
        }
      }
      return of(error);
    })
  );
}

