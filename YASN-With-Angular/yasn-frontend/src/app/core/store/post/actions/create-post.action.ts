import {Action} from "@ngrx/store";
import {PostActionTypes} from "./action.type";

export class CreatePost implements Action {
  public readonly type: string;

  constructor(public payload: any) {
    this.type = PostActionTypes.CREATE_POST;
  }

}
