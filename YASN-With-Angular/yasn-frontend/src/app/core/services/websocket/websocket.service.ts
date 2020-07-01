import {Injectable} from '@angular/core';
import {Observable, Subscriber} from "rxjs";
import {Client} from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {WebSocketEndpoints} from "../../../shared/common/WebSocketEndpoints";
import {UserProfileState} from "../../store/userProfile/state/user-profile.state";
import {Store} from "@ngrx/store";
import {AppState} from "../../store/app.state";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private loggedInProfile: UserProfileState;

  private stompClient: Client = null;

  private postData$: Subscriber<any>;

  private newFrRequestData$: Subscriber<any>;

  private commentsData$: Subscriber<any>;

  private likesData$: Subscriber<any>;

  private unlikesData$: Subscriber<any>;

  private userSpecificData$: Subscriber<any>

  constructor(private store$: Store<AppState>) {
  }

  connect() {
    const _this = this;
    this.store$.select('userProfile').subscribe(value => this.loggedInProfile = value);

    _this.stompClient = new Client({
      brokerURL: WebSocketEndpoints.websocketStompFactory,
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    _this.stompClient.webSocketFactory = function () {
      return new SockJS(WebSocketEndpoints.websocketSockJSFactory);
    };

    _this.stompClient.onWebSocketError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.onStompError = function (ev) {
      console.log(ev);
    };

    _this.stompClient.onConnect = function (frame) {

      _this.stompClient.subscribe(WebSocketEndpoints.topicCreatedNewPost,function (data) {
        _this.postData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicNotificationFriendRequest,function (data) {
        _this.newFrRequestData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicLike,function (data) {
        _this.likesData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicUnLike,function (data) {
        _this.unlikesData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicCommentOnPost, function (data) {
        _this.commentsData$.next(data.body);
      });

      _this.stompClient.subscribe(WebSocketEndpoints.topicUserSpecific + _this.loggedInProfile.profileUsername, function (data) {
        _this.userSpecificData$.next(data.body);
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
