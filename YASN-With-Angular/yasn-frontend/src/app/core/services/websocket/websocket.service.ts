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

  private stompClient: Client = null;

  private postData$: Subscriber<any>;

  private notificationsData$: Subscriber<any>;

  private commentsData$: Subscriber<any>;

  private likesData$: Subscriber<any>;

  private unlikesData$: Subscriber<any>;

  constructor() {
  }

  connect() {
    const _this = this;

    _this.stompClient = new Client({
      brokerURL: EndpointUrls.websocketStompFactory,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    _this.stompClient.webSocketFactory = function () {
      return new SockJS(EndpointUrls.websocketSockJSFactory);
    };

    _this.stompClient.onWebSocketError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.onStompError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.onConnect = function (frame) {

      _this.stompClient.subscribe(NotificationsEndpointTypes.createNewWallPost, function (data) {
        _this.postData$.next(data.body);
      });

      _this.stompClient.subscribe(NotificationsEndpointTypes.sendNewFriendRequest, function (data) {
        _this.notificationsData$.next(data.body);
      });

      _this.stompClient.subscribe(NotificationsEndpointTypes.like, function (data) {
        _this.likesData$.next(data.body);
      });

      _this.stompClient.subscribe(NotificationsEndpointTypes.unlike, function (data) {
        _this.unlikesData$.next(data.body);
      });

      _this.stompClient.subscribe(NotificationsEndpointTypes.commentOnPost, function (data) {
        _this.commentsData$.next(data.body);
      });

    };

    _this.stompClient.activate();
  }

  disconnect() {

    if (this.stompClient != null) {
      this.stompClient.deactivate();
    }

  }

  getPostsData<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.postData$ = subscriber);
  }

  getFriendRequestsData<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.notificationsData$ = subscriber);
  }

  doLike<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.likesData$ = subscriber);
  }

  doUnlike<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.unlikesData$ = subscriber);
  }

  getCommentsData<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.commentsData$ = subscriber);
  }


}
