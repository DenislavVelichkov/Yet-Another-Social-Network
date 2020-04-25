import {initialState, PostState} from "../state/post.state";
import {PostActions, PostActionTypes} from "../actions/action.type";

export function postsReducer(state: PostState = initialState,
                             action: PostActions) {

  switch (action.type) {
    case PostActionTypes.GET_ALL_POSTS:

      return getAllPosts(state, action.payload);
    case PostActionTypes.CREATE_POST:

      return createPost(state, action.payload);
    default:
      return state;
  }

}

function getAllPosts(state, payload) {

  return Object.assign({}, state, payload);
}

function createPost(state, payload) {

  return Object.assign({}, state, payload)
}
