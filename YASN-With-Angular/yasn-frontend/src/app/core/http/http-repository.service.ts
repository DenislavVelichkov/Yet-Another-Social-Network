import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EnvironmentUrlService} from "./environment-url.service";
import {take} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class HttpRepositoryService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private formHeaders = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  constructor(private http: HttpClient,
              private envUrl: EnvironmentUrlService) {
  }

  private static createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }

  public get<T>(route: string): Observable<T> {

    return this.http.get<T>(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      {headers: this.headers});
  }

  public create<T>(route: string, body: any): Observable<T> {

    return this.http.post<T>(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body);
  }

  /* public postWithForm(route: string, body) {

     return this.http.post(
       HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
       body,
       {
         headers: this.formHeaders,
         observe: "response"
       }
     ).pipe(take(1))
   }*/

  public loginRequest(route: string, body: any) {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {headers: this.headers, observe: "response"});
  }

  public update(route: string, body) {
    return this.http.put(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress),
      body,
      {})
      .pipe(take(1));
  }

  public delete(route: string) {
    return this.http.delete(
      HttpRepositoryService.createCompleteRoute(
        route,
        this.envUrl.apiEndPointAddress))
      .pipe(take(1));
  }

}
