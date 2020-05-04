import {Injectable} from '@angular/core';
import {HttpRepositoryService} from "../../http/http-repository.service";
import {EndpointUrls} from "../../../shared/common/EndpointUrls";
import {Post} from "../../store/post/Post";
import {throwError} from "rxjs";
import {CreatePost} from "../../store/post/actions/create-post.action";
import {PostBindingModel} from "../../../shared/models/post/PostBindingModel";
import {AppState} from "../../store/app.state";
import {Store} from "@ngrx/store";
import {take} from "rxjs/operators";
import {DisplayAllPostsAction} from "../../store/post/actions/display-all-posts.action";
import {CommentBindingModel} from "../../../shared/models/comment/CommentBindingModel";
import {Comment} from "../../store/comment/Comment";

@Injectable({providedIn: "root"})
export class NewsFeedService {

  constructor(private httpRepo: HttpRepositoryService,
              private store: Store<AppState>) {
  }

  getAllNewsFeeds(): void {
    this.httpRepo.get<Post[]>(EndpointUrls.pullAllNews)
      .pipe(take(1))
      .subscribe((value: Post[]) => {
        this.store.dispatch(new DisplayAllPostsAction({allWallPosts: value, loading: true}))
      }, error => throwError(error));
  }

  createPost(postModel: PostBindingModel,
             userProfileId: string,
             payload: Array<File>) {

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
      .subscribe(post => {
        this.store.dispatch(new CreatePost({post: [post], loading: false}))
      });
  }

  createComment(userProfileId: string,
                commentModel: CommentBindingModel,
                pictures: Array<File>) {
    commentModel.commentOwnerId = userProfileId;
    let commentBlob = new Blob([JSON.stringify(commentModel)], {type: 'application/json'});

    let commentForm = new FormData();
    commentForm.append("comment", commentBlob);

    pictures.forEach(pic => {
      commentForm.append("commentPicture", pic, `${pic.name}`);
    })

    this.httpRepo.create<Comment>(EndpointUrls.postComment, commentForm)
      .pipe(take(1)).subscribe(data => {
        console.log(data)
    });
  }
}
