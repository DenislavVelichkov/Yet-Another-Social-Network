import {Action} from "@ngrx/store";
import {ActionTypes} from "./posts.actions";


export class CreatePost implements Action {
  public readonly type: string;

  constructor(public payload: any) {
    this.type = ActionTypes.CREATE_POST;
  }

}
