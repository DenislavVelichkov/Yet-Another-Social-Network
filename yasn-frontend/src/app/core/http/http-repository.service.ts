import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EnvironmentUrlService} from './environment-url.service';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class HttpRepositoryService {

  private applicationJSON = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private formHeaders = new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  private formData = new HttpHeaders({
    'Content-Type': 'multipart/form-data'
  });

  private static createCompleteRoute(route: string, envAddress: string) {
    return `${envAddress}${route}`;
  }

  constructor(private http: HttpClient,
              private envUrl: EnvironmentUrlService) {
  }

  public get<T>(route: string): Observable<T> {

    return this.http.get<T>(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      {
        headers: this.applicationJSON,
        withCredentials: true
      });
  }

  public create<T>(route: string, body: any, mymeType?: string): Observable<T> {
    const headers = {formData: this.formData, applicationJSON: this.applicationJSON};

    if (mymeType === 'formData') {
      return this.http.post<T>(
        HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
        body, {
          withCredentials: true
        });
    } else {
      return this.http.post<T>(
        HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
        body, {
          headers: headers.applicationJSON,
          withCredentials: true
        });
    }

  }

  /* public postWithForm(route: string, body) {

     return this.http.post(
       HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
       body,
       {
         applicationJSON: this.formHeaders,
         observe: "response"
       }
     ).pipe(take(1))
   }*/

  public loginRequest(route: string, body: any) {

    return this.http.post(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body,
      {headers: this.applicationJSON, withCredentials: true, observe: 'response'});
  }

  public update<T>(route: string, body: any): Observable<T> {
    return this.http.put<T>(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      body, {headers: this.applicationJSON, withCredentials: true});
  }

  public delete<T>(route: string): Observable<T> {
    return this.http.delete<T>(
      HttpRepositoryService.createCompleteRoute(route, this.envUrl.apiEndPointAddress),
      {headers: this.applicationJSON, withCredentials: true});
  }

}
