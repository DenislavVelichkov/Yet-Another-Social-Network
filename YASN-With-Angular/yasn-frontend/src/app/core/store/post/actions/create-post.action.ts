import {Action} from "@ngrx/store";
import {ActionTypes} from "./posts.actions";


export class CreatePost implements Action {
  public readonly type: string = ActionTypes.CREATE_POST;

  constructor(public payload: any) {

  }

}
