import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from './services/auth.service';
import {catchError, of, switchMap} from 'rxjs';
import {Router} from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (
  req, next) => {

  const authService = inject(AuthService);
  const router = inject(Router);
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

      if(error.status == 404) {
        router.navigate(['/page-not-found']);
      }

      //TODO: Change to Connection with server error / component
      if(error.status == 0) {
        router.navigate(['/page-not-found']);
      }

      return of(error);
    })
  );
}

