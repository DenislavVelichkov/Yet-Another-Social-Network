export interface Principal {
  userName: string,
  userProfileId: string,
  role: string,
  rememberMe: boolean,
  _token: string,
  tokenExpirationDate: Date,
}
