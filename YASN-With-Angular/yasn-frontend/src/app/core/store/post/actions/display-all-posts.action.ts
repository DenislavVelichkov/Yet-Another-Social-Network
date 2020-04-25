import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";

export class DisplayAllPostsAction implements Action{
  public readonly type: string;

  constructor(public payload: any) {
    this.type = PostActionTypes.GET_ALL_POSTS;
  }

}
