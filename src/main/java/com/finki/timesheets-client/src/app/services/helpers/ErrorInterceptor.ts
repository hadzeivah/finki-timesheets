import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {Injectable} from '@angular/core';
import {AuthService} from "../auth.service";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if ([401, 403].indexOf(err.status) !== -1) {
        // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
        this.authService.logout();
        location.reload(true);
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    }))
  }
}
