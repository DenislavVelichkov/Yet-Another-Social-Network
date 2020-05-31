export const initialState: GuestProfileState = {
  userProfileId: null,
  userFullName: null,
  avatarPictureUrl: null,
  coverPictureUrl: null,
}

export interface GuestProfileState {
  userProfileId: string,
  userFullName: string,
  avatarPictureUrl: string,
  coverPictureUrl: string,
}
