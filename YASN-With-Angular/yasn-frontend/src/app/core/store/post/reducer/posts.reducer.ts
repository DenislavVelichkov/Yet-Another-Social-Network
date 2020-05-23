import {initialState, PostState} from "../state/post.state";
import {PostActions, PostActionTypes} from "../actions/action.type";
import {Post} from "../Post";
import {PostComment} from "../PostComment";
import produce from "immer";

export function postsReducer(state: PostState = initialState,
                             action: PostActions) {

  switch (action.type) {

    case PostActionTypes.GET_ALL_POSTS:
      return getAllPosts(state, action.payload);

    case PostActionTypes.CREATE_POST:
      return createPost(state, action.payload);

    case PostActionTypes.COMMENT_ON_POST:
      return createComment(state, action.payload);

    case PostActionTypes.LIKE_A_POST:
      return likePost(state, action.payload);

    default:
      return state;
  }

}

function getAllPosts(state, payload) {

  return Object.assign({}, state, payload);
}

function createPost(state, payload) {
  let newPost: PostState = {
    allWallPosts: state.allWallPosts.concat(payload.post)
      .sort((a: Post, b: Post) => {
        let result = compareDatesDesc(a.createdOn, b.createdOn);

        return result !== 0 ? result : -1;
      }),
    loading: payload.loading
  }

  return newPost;
}

function createComment(state, payload) {

  return produce(state, draftState => {
    draftState.allWallPosts
      .find(post => post.id === payload.comment.wallPostId)
      .comments.push(payload.comment);

    draftState.allWallPosts.find(post => post.id === payload.comment.wallPostId)
      .comments.sort(
      (a: PostComment, b: PostComment) => compareDatesDesc(a.createdOn, b.createdOn))

    draftState.loading = payload.loading;
  });
}

function likePost(state, payload) {
  return produce(state, draftState => {
    draftState.allWallPosts
      .find(post => post.id === payload.postId)
      .likesCount++

    draftState.loading = payload.loading;
  });
}

function compareDatesDesc(dateA: Date, dateB: Date) {
  let a = new Date(dateA)
  let b = new Date(dateB)

  return b.getTime() - a.getTime();
}
