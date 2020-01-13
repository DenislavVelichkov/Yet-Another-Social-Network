import {ActionTypes} from "./posts.actions";

export class AllPostsAction {
  public readonly type: string = ActionTypes.GET_ALL_POSTS;

  constructor(public payload: any) {
  }

}
