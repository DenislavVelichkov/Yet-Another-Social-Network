import {Post} from "../Post";

export const initialState: PostState = {
  allWallPosts: new Array<Post>(),
};

export interface PostState {
  allWallPosts: Array<Post>;
}
