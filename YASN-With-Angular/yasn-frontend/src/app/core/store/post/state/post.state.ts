import {Post} from "../Post";

export const initialState: PostState = {
  allWallPosts: [],
  loading: false
};

export interface PostState {
  allWallPosts: Post[];
  loading: boolean;
}
