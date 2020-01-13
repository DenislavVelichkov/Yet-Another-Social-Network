import {PostsState} from "../state/posts.state";
import {ActionTypes} from "../actions/posts.actions";
import {PostActions} from "../actions/action.type";

const initialState: PostsState = {all: []};

export function postsReducer(
  state: PostsState = initialState,
  action: PostActions) {


  switch (action.type) {
    case ActionTypes.GET_ALL_POSTS:
      return getAllPosts(initialState, action.payload);
    case ActionTypes.CREATE_POST:
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
