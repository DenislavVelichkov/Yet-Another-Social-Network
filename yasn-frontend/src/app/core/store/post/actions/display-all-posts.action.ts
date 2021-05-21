import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";
import {Post} from "../Post";

export class DisplayAllPostsAction implements Action {
  public readonly type: string;

  constructor(public payload: { allWallPosts: Post[] }) {
    this.type = PostActionTypes.GET_ALL_POSTS;
  }

}
