export const initialState: UserProfileState = {
  userProfileId: null,
  userFullName: null,
  avatarPictureUrl: null,
  coverPictureUrl: null,
}

export interface UserProfileState {
  userProfileId: string,
  userFullName: string,
  avatarPictureUrl: string,
  coverPictureUrl: string,
}
