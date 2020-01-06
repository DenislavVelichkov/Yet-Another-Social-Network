import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let xhr = req.clone(
      {headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')}
    );

    return next.handle(xhr);
  }
}
