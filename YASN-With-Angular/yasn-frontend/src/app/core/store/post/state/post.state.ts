import {Post} from "../Post";

export const initialState: PostState = {
  allWallPosts: []
};

export interface PostState {
  allWallPosts: Post[];
}
