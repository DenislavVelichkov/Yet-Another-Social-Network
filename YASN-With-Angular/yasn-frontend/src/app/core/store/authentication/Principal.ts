export interface Principal {
  role: string,
  userProfileId: string,
  fullName: string,
  avatarUrl: string,
  coverPictureUrl: string,
  rememberMe: boolean,
  _token: string,
  tokenExpirationDate: Date,
}
