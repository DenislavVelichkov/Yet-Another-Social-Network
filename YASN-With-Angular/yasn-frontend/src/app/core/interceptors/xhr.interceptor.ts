import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let xhr = req.clone({
        setHeaders: {
          'Access-Control-Allow-Origin': ['http://localhost:8000', 'http://localhost:4200'],
          'X-Requested-With': 'XMLHttpRequest'
        }
      });

    return next.handle(xhr);
  }
}
