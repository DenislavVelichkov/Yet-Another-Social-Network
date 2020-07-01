import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";
import {Post} from "../Post";

export class CreatePost implements Action {
  public readonly type: string;

  constructor(public payload: {post: Post}) {
    this.type = PostActionTypes.CREATE_POST;
  }

}
