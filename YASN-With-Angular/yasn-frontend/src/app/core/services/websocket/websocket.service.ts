import {Injectable} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {EnvironmentUrlService} from "../../http/environment-url.service";
import {Stomp} from "@stomp/stompjs";
import * as SockJS from "sockjs-client";


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  data: Subscription;

  private stompClient = null;

  constructor(private environment: EnvironmentUrlService) { }

  connect(path: string) {
    this.stompClient = Stomp.client(EndpointUrls.websocketStompFactory, ['v10.stomp', 'v11.stomp']);

    this.stompClient.debug = function(str) {
      console.log(str);
    };

    this.stompClient.webSocketFactory = function () {
      return new WebSocket(EndpointUrls.websocketStompFactory);
    }
    this.stompClient.webSocketFactory = function () {
      return new SockJS(EndpointUrls.websocketSockJSFactory);
    }

    const _this = this;

    _this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe(path, function (data) {
        _this.data = data.body;

        console.log(JSON.parse(data.body));
      });
    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }

  getData<T>(): Observable<T> {
    return new Observable<T>(subscriber => this.data = subscriber);
  }

}
