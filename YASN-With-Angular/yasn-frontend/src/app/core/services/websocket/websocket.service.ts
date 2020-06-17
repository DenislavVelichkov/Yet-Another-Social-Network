import {Injectable} from '@angular/core';
import {Observable, Subscriber} from "rxjs";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {EnvironmentUrlService} from "../../http/environment-url.service";
import {Client} from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  data: Subscriber<any>;

  stompClient: Client = null;

  constructor(private environment: EnvironmentUrlService) {
  }

  connect(path: string) {
    const token = JSON.parse(localStorage.getItem('activeUser'))._token;

    this.stompClient = new Client({
      brokerURL: EndpointUrls.websocketStompFactory,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      connectHeaders: {Authorization: token}
    });

    this.stompClient.webSocketFactory = function () {
      return new SockJS(EndpointUrls.websocketSockJSFactory);
    };

    const _this = this;

    _this.stompClient.onConnect = function (f) {
      _this.stompClient.subscribe(path, function (data) {
        _this.data.next(data.body);
      })
    };

    _this.stompClient.onWebSocketError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.onStompError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.activate();
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.deactivate();
    }
  }

  getData<T>(): Observable<T> {
    return new Observable<T>(subscriber => this.data = subscriber);
  }

}
