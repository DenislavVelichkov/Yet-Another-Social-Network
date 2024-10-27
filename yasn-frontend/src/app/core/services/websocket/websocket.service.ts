import {Injectable} from '@angular/core';
import {Observable, Subscriber} from 'rxjs';
import {Client} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {WebSocketEndpoints} from '../../../shared/common/WebSocketEndpoints';
import {Store} from '@ngrx/store';
import {AppState} from '../../store/app.state';
import {AuthService} from '../authentication/auth.service';
import {EnvironmentUrlService} from '../../http/environment-url.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private loggedInProfileUsername: string;

  stompClient: Client = null;

  private postData$: Subscriber<any>;

  private newFrRequestData$: Subscriber<any>;

  private commentsData$: Subscriber<any>;

  private likesData$: Subscriber<any>;

  private unlikesData$: Subscriber<any>;

  private userSpecificData$: Subscriber<any>;

  constructor(private store$: Store<AppState>,
              private auth: AuthService,
              private envUrl: EnvironmentUrlService) {
    this.loggedInProfileUsername = this.auth.getActiveUser().userName;
  }

  async connect() {
    const _this = this;

    _this.stompClient = await new Client({
      brokerURL: this.envUrl.websocketStompFactory,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    _this.stompClient.webSocketFactory = function () {
      return new SockJS.default(_this.envUrl.websocketSockJSFactory);
    };

    _this.stompClient.onWebSocketError = await function (ev) {
      console.log(ev);
    };

    _this.stompClient.onStompError = await function (ev) {
      console.log(ev);
    };

    _this.stompClient.onConnect = await function (frame) {

      _this.stompClient.subscribe(WebSocketEndpoints.topicUserSpecific + _this.loggedInProfileUsername, function (data) {
        _this.userSpecificData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicCreatedNewPost, function (data) {
        _this.postData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicNotificationFriendRequest, function (data) {
        _this.newFrRequestData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicLike, function (data) {
        _this.likesData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicUnLike, function (data) {
        _this.unlikesData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicCommentOnPost, function (data) {
        _this.commentsData$.next(data.body);
      });

    };

    await _this.stompClient.activate();
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

    return new Observable<T>(subscriber => this.newFrRequestData$ = subscriber);
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

  getPostNotificationData<T>(): Observable<T> {

    return new Observable<T>(subscriber => this.userSpecificData$ = subscriber);
  }


}
