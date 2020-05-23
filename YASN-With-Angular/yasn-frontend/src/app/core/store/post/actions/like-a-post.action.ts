import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";
import {LikeAPost} from "../LikeAPost";

export class LikeAPostAction implements Action {
  readonly type: string;

  constructor(public payload: LikeAPost) {
    this.type = PostActionTypes.LIKE_A_POST;
  }


}
