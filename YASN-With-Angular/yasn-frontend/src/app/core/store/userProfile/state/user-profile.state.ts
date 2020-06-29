export const initialState: UserProfileState = {
  userProfileId: null,
  userFullName: null,
  avatarPictureUrl: null,
  coverPictureUrl: null,
  profileFriends: [],
}

export interface UserProfileState {
  userProfileId: string;
  userFullName: string;
  avatarPictureUrl: string;
  coverPictureUrl: string;
  profileFriends: [];
}
