import {Post} from "../Post";

export const initialState: PostState = {
  all: []
};

export interface PostState {
  all: Post[];
}
