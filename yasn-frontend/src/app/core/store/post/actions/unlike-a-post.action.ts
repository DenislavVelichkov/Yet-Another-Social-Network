import {Action} from "@ngrx/store";
import {LikeAPost} from "../LikeAPost";
import {PostActionTypes} from "./action.type";

export class UnlikeAPostAction implements Action {
  public readonly type: string;

  constructor(public payload: LikeAPost) {
    this.type = PostActionTypes.UNLIKE_A_POST;
  }

}
