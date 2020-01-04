import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const csrfToken = this.getCsrfCookie('XSRF-TOKEN');
    const clonedRequest = req.clone(
      {headers: req.headers.append('_csrf', csrfToken)}
    );

    return next.handle(clonedRequest);
  }

  getCsrfCookie(name) {
    const csrfCookie = document.cookie;

    if (!csrfCookie) {
      return null;
    }

    const cleanCsrfToken = csrfCookie.split(';')
      .map(c => c.trim())
      .filter(c => c.startsWith(name + '='));

    if (cleanCsrfToken.length === 0) {
      return null;
    }

    return decodeURIComponent(cleanCsrfToken[0].split('=')[1]);
  }

}
