import {Injectable} from '@angular/core';
import {Observable, Subscriber} from "rxjs";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {Client} from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {NotificationsEndpointTypes} from "../notification/NotificationTypes";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  postData: Subscriber<any>;

  notificationsData: Subscriber<any>;

  stompClient: Client = null;

  constructor() {
  }

 async connect(path: string) {

    this.stompClient = new Client({
      brokerURL: EndpointUrls.websocketStompFactory,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.stompClient.webSocketFactory = function () {
      return new SockJS(EndpointUrls.websocketSockJSFactory);
    };

    const _this = this;

    _this.stompClient.onConnect = function () {

      switch (path) {
        case NotificationsEndpointTypes.createNewWallPost:
          _this.stompClient.subscribe(NotificationsEndpointTypes.createNewWallPost, function (data) {
            _this.postData.next(data.body);
          })
          break;
        case NotificationsEndpointTypes.sendNewFriendRequest:
          _this.stompClient.subscribe(NotificationsEndpointTypes.sendNewFriendRequest, function (data) {
            _this.notificationsData.next(data.body);
          })
          break
      }

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

  getPostsData<T>(): Observable<T> {
    return new Observable<T>(subscriber => this.postData = subscriber);
  }

  getFriendRequestsData<T>(): Observable<T> {
    return new Observable<T>(subscriber => this.notificationsData = subscriber);
  }

}
