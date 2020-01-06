import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";

@Injectable()
export class HttpRepositoryService {

  constructor(private http: HttpClient, private envUrl: EnvironmentUrlService) { }

  public getData(route: string) {
    return this.http.get(this.createCompleteRoute(
      route,
      this.envUrl.apiEndPointAddress));
  }

  public create(route: string, body) {
    return this.http.post(
      this.createCompleteRoute(
                                route,
                                this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
  }

  public update(route: string, body) {
    return this.http.put(
      this.createCompleteRoute(
                                route,
                                this.envUrl.apiEndPointAddress),
      body,
      this.generateHeaders(body));
  }

  public delete(route: string) {
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
    ];

    return {
      headers: new HttpHeaders(...headers)
    };
  }
}
