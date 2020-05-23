import {CreatePost} from "./create-post.action";
import {CommentOnPostAction} from "./comment-on-post.action";
import {DisplayAllPostsAction} from "./display-all-posts.action";
import {type} from "../../../util/util";
import {LikeAPostAction} from "./like-a-post.action";

export const PostActionTypes = {
  GET_ALL_POSTS: type('[POSTS] Get All'),
  CREATE_POST: type('[POSTS] Create Post'),
  COMMENT_ON_POST: type('[POSTS] Comment on Post'),
  LIKE_A_POST: type('[POSTS] Like A Post')
};

export type PostActions =
  CreatePost
  | DisplayAllPostsAction
  | CommentOnPostAction
  | LikeAPostAction;
