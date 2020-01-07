import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {Observable} from "rxjs";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) { }

  public getData(route: string, headers) {

    return this.http.get(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress),{headers:headers});
  }

  public create(route: string, body): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
                                route,
                                this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
  }

  public loginRequest(route: string, body, headers): Observable<Object> {
    return this.http.post(
      this.createCompleteRoute(
                                route,
                                this.envUrl.apiEndPointAddress),
      body,
      {headers, responseType:"text"});
  }

  public update(route: string, body): Observable<Object> {
    return this.http.put(
      this.createCompleteRoute(
                                route,
                                this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
  }

  public delete(route: string): Observable<Object> {
    return this.http.delete(
      this.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress));
  }

  private createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }

  private generateHeaders(body) {
    if (body instanceof FormData) { return; }

    const headers = [
      {
        'Content-Type': 'application/json'
      },
      {
        'withCredentials': 'true'
      }
    ];

    return {
      headers: new HttpHeaders(...headers)
    };
  }
}
