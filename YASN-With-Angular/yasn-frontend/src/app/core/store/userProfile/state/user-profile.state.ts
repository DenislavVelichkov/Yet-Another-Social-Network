
export const initialState: UserProfileState = {
  userFullName: null,
  avatarPictureUrl: null,
  coverPictureUrl: null,
}

export interface UserProfileState {
  userFullName: string,
  avatarPictureUrl: string,
  coverPictureUrl: string,
}
