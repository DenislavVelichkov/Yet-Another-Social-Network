import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpXsrfTokenExtractor} from "@angular/common/http";
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  constructor(private cookieService: CookieService,
              private tokenExtractor: HttpXsrfTokenExtractor) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let requestMethod: string = req.method.toLowerCase();

    if (requestMethod
      && (requestMethod === 'post'
      || requestMethod === 'delete'
      || requestMethod === 'put')) {
      const headerName = 'X-XSRF-TOKEN';
      let token = this.tokenExtractor.getToken();
      if (token !== null && !req.headers.has(headerName)) {
        req = req.clone({headers: req.headers.set(headerName, token)});
      }
    }

    let xhr = req.clone({
      setHeaders: {
        'Access-Control-Allow-Origin': 'http://localhost:8000',
        'X-Requested-With': 'XMLHttpRequest',
      },
      withCredentials: true
    });

    return next.handle(xhr);
  }
}
