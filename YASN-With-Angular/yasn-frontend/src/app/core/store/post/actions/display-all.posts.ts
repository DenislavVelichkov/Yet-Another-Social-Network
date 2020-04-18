import {ActionTypes} from "./posts.actions";

export class DisplayAllPosts {
  public readonly type: string;

  constructor(public payload: any) {
    this.type = ActionTypes.GET_ALL_POSTS;
  }

}
