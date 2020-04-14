export interface Principal {
  userName: string,
  userId: string,
  role: string,
  rememberMe: boolean,
  _token: string,
  tokenExpirationDate: Date,
}
