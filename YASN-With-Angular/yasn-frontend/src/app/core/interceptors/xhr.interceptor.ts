import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let xhr = req.clone({
      setHeaders: {
        'X-Requested-With': 'XMLHttpRequest',
      },
      withCredentials: true
    });

    return next.handle(xhr);
  }
}
