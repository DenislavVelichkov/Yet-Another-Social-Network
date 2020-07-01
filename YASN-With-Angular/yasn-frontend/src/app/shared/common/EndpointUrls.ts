export const EndpointUrls = {
  loginUser: "/api/user/login",
  registerUser: "/api/user/register",
  postToPublicWall: "/api/home/news-feed/post",
  createPostNotification: "/api/notification/new-post-created",
  pullAllNews: "/api/home/all-news/",
  getAllNotificationsForLoggedInUser: "/api/notification/get-all-notifications/",
  postComment: "/api/home/news-feed/post/comment",
  likeAPost: "/api/actions/likes/likeAPost",
  unLikeAPost: "/api/actions/likes/unLikeAPost",
  userProfileTimeline: "/api/user-profile/timeline/",
  userProfileGuest: "/api/user-profile/guest/",
  selectUserProfile: "/api/user-profile/",
  sendFriendRequest: "/api/notification/send-friend-request",
  acceptFriendRequest: "/api/notification/accept-friend/",
  addFriend: "/api/actions/add-friend",
  unfriendRequest: "/api/notification/unfriend/",
  searchProfile: "/api/search/user-profile/",
  friendshipStatus: "/api/actions/check-friendship/",
  deleteNotification: "/api/notification/delete-notification/",
  editNotification: "/api/notification/edit-notification/",
  getNotificationSenderFriends: "/api/user-profile/friends/"
}
