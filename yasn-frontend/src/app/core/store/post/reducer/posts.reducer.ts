import {initialState, PostState} from "../state/post.state";
import {PostActions, PostActionTypes} from "../actions/action.type";
import {Post} from "../Post";
import {PostComment} from "../PostComment";
import produce, {enableMapSet} from "immer";

enableMapSet();

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

    case PostActionTypes.UNLIKE_A_POST:
      return unLikePost(state, action.payload);

    default:
      return state;
  }

}

function getAllPosts(state, payload) {

  return Object.assign({}, state, payload);
}

function createPost(state: PostState, payload: any) {

  return produce(state, draftState => {
    draftState.allWallPosts
      .push(payload.post)
    draftState.allWallPosts
      .sort((postA: Post, postB: Post) => compareDatesDesc(postA.createdOn, postB.createdOn))
  });
}

function createComment(state, payload) {

  return produce(state, draftState => {
    draftState.allWallPosts
      .find(post => post.id === payload.comment.wallPostId)
      .comments.push(payload.comment);

    draftState.allWallPosts.find(post => post.id === payload.comment.wallPostId)
      .comments.sort(
      (a: PostComment, b: PostComment) => compareDatesDesc(a.createdOn, b.createdOn))
  });
}

function likePost(state, payload) {

  return produce(state, draftState => {
    let newPost = draftState.allWallPosts.find(post => post.id === payload.postId);
    newPost.likesCount++;

    let activeUserId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;

    if (activeUserId === payload.userProfileId) {
      newPost.postLikedByCurrentUser = true;
    }

    draftState.allWallPosts
      .map(p => p.id === newPost.id || newPost);
  });
}

function unLikePost(state, payload) {

  return produce(state, draftState => {
    let newPost = draftState.allWallPosts.find(post => post.id === payload.postId);
    newPost.likesCount--;

    let activeUserId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;

    if (activeUserId === payload.userProfileId) {
      newPost.postLikedByCurrentUser = false;
    }

    draftState.allWallPosts
      .map(p => p.id === newPost.id || newPost);
  });
}

function compareDatesDesc(dateA: Date, dateB: Date) {
  let a = new Date(dateA)
  let b = new Date(dateB)

  return b.getTime() - a.getTime();
}
