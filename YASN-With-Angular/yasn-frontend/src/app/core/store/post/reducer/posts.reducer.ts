import {PostsState} from "../state/posts.state";
import * as PostsActions from "../actions/posts.action";

const initialState: PostsState = {all: []};

export function postsReducer(
  state: PostsState = initialState,
  action: PostsActions.Type) {


  switch (action.type) {
    case PostsActions.GET_ALL_POSTS:
      return getAllPosts(initialState, action.payload);
    case PostsActions.CREAT_POSTS:
      return createPost(initialState, action.payload);
    default:
      return state;
  }
}

function getAllPosts(state, action) {
  return Object.assign({}, state, action)
}

function createPost(state, action) {
  return Object.assign({}, state, action)
}
