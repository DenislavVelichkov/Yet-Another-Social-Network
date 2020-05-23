import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";
import {PostComment} from "../PostComment";

export class CommentOnPostAction implements Action {
  public readonly type: string;

  constructor(public payload: { comment: PostComment, loading: boolean }) {
    this.type = PostActionTypes.COMMENT_ON_POST;
  }


}
