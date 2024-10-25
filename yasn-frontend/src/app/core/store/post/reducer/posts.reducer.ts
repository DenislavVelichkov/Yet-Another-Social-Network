import {initialState, PostState} from "../state/post.state";
import {PostActions, PostActionTypes} from "../actions/action.type";
import {Post} from "../Post";
import {PostComment} from "../PostComment";

export function postsReducer(state: PostState = initialState,
                             action: PostActions) {

  switch (action.type) {

    case PostActionTypes.GET_ALL_POSTS:
      return getAllPosts(state, action.payload);

    case PostActionTypes.CREATE_POST:
      return createPost(state, action.payload);

    case PostActionTypes.COMMENT_ON_POST:
      return createComment(state, action.payload as { comment: PostComment });

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

  return {
    ...state,
    allWallPosts: [
      ...state.allWallPosts,
      payload.post
    ].sort((postA: Post, postB: Post) => compareDatesDesc(postA.createdOn, postB.createdOn))
  };
}

function createComment(state: PostState, payload: { comment: PostComment } | any): PostState {
  if (!payload.comment) {
    return state; // Return unchanged state if payload doesn't have a comment
  }

  return {
    ...state,
    allWallPosts: state.allWallPosts.map(post => {
      if (post.id === payload.comment.wallPostId) {
        return {
          ...post,
          comments: [...post.comments, payload.comment]
            .sort((a: PostComment, b: PostComment) => compareDatesDesc(a.createdOn, b.createdOn))
        };
      }
      return post;
    })
  };
}

function likePost(state, payload) {
  return {
    ...state,
    allWallPosts: state.allWallPosts.map(post => {
      if (post.id === payload.postId) {
        const activeUserId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;
        return {
          ...post,
          likesCount: post.likesCount + 1,
          postLikedByCurrentUser: activeUserId === payload.userProfileId ? true : post.postLikedByCurrentUser
        };
      }
      return post;
    })
  };
}

function unLikePost(state, payload) {
  return {
    ...state,
    allWallPosts: state.allWallPosts.map(post => {
      if (post.id === payload.postId) {
        const activeUserId = JSON.parse(localStorage.getItem("activeUser"))._userProfileId;
        return {
          ...post,
          likesCount: post.likesCount - 1,
          postLikedByCurrentUser: activeUserId === payload.userProfileId ? false : post.postLikedByCurrentUser
        };
      }
      return post;
    })
  };
}

function compareDatesDesc(dateA: Date, dateB: Date) {
  let a = new Date(dateA)
  let b = new Date(dateB)

  return b.getTime() - a.getTime();
}
