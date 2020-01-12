import {Action} from "@ngrx/store";

export const GET_ALL_POSTS: string = '[POSTS] Get All';
export const CREAT_POSTS: string = '[POSTS] Create Post';
export type Type = PostsActions;

export class PostsActions implements Action {
  readonly type: string;

  constructor(public payload: any) {

  }

}
