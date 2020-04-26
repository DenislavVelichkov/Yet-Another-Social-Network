import {initialState, PostState} from "../state/post.state";
import {PostActions, PostActionTypes} from "../actions/action.type";
import {Post} from "../Post";

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
  let newPost: PostState = {
    allWallPosts: state.allWallPosts
      .concat(payload.post)
      .sort((a: Post, b: Post) => {
        let result = compareDatesDesc(a.createdOn, b.createdOn);

        return result !== 0 ? result : -1;
      }),
    loading: payload.loading
  }

  return newPost;
}

function compareDatesDesc(dateA: Date, dateB: Date) {
  let a = new Date(dateA)
  let b = new Date(dateB)

  return b.getTime() - a.getTime();
}
