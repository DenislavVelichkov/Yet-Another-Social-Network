import {CreatePost} from "./create-post.action";
import {DisplayAllPostsAction} from "./display-all-posts.action";
import {type} from "../../../util/util";

export const PostActionTypes = {
  GET_ALL_POSTS: type('[POSTS] Get All'),
  CREATE_POST: type('[POSTS] Create Post')
};

export type PostActions =
  CreatePost
  | DisplayAllPostsAction;
