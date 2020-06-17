import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {Post} from "../../store/post/Post";
import {throwError} from "rxjs";
import {PostBindingModel} from "../../../shared/models/post/PostBindingModel";
import {AppState} from "../../store/app.state";
import {Store} from "@ngrx/store";
import {take} from "rxjs/operators";
import {DisplayAllPostsAction} from "../../store/post/actions/display-all-posts.action";
import {CommentBindingModel} from "../../../shared/models/comment/CommentBindingModel";
import {CommentOnPostAction} from "../../store/post/actions/comment-on-post.action";
import {PostComment} from "../../store/post/PostComment";
import {StartLoadingAction} from "../../store/loading/actions/start-loading.action";
import {StopLoadingAction} from "../../store/loading/actions/stop-loading.action";
import {WebsocketService} from "../websocket/websocket.service";

@Injectable({providedIn: "root"})
export class NewsFeedService {

  constructor(private httpRepo: HttpRepositoryService,
              private store$: Store<AppState>,
              private websocketService: WebsocketService) {
  }

  getAllNewsFeeds(userProfileId: string): void {

    this.httpRepo.get<Post[]>(EndpointUrls.pullAllNews + userProfileId)
      .pipe(take(1))
      .subscribe((value: Post[]) => {
        this.store$.dispatch(new DisplayAllPostsAction({allWallPosts: value}))
      }, error => throwError(error));
  }

  createPost(postModel: PostBindingModel,
             userProfileId: string,
             payload: File[]) {

    let formData = new FormData();
    postModel.postOwnerId = userProfileId;

    let postBlobModel = new Blob(
      [JSON.stringify(postModel)],
      {type: 'application/json'}
    );

    formData.append("post", postBlobModel);
    payload.forEach(value => {
      formData.append("postPicture", value, `${value.name}`);
    })

    this.httpRepo.create<Post>(EndpointUrls.postToPublicWall, formData)
      .pipe(take(1))
      .subscribe(post => {
      }, error => {
        this.store$.dispatch(new StopLoadingAction({loading: false}));
        console.log(throwError(error));
      });
  }

  createComment(userProfileId: string,
                commentModel: CommentBindingModel,
                pictures: File[]) {
    this.store$.dispatch(new StartLoadingAction({loading: true}));

    commentModel.commentOwnerId = userProfileId;
    let commentBlob = new Blob([JSON.stringify(commentModel)], {type: 'application/json'});

    let commentForm = new FormData();
    commentForm.append("comment", commentBlob);

    pictures.forEach(pic => {
      commentForm.append("commentPicture", pic, `${pic.name}`);
    })

    this.httpRepo.create<PostComment>(EndpointUrls.postComment, commentForm)
      .pipe(take(1)).subscribe((data: PostComment) => {
      this.store$.dispatch(new CommentOnPostAction({comment: data}));
    }, error => {
      this.store$.dispatch(new StopLoadingAction({loading: false}));
      throwError(error);
    });

    this.store$.dispatch(new StopLoadingAction({loading: false}));
  }
}
