export class UserAuthModel {

  private _userProfileId: string

  private _role: string

  private _userName: string

  private _rememberMe: boolean

  private _token: string

  private _tokenExpirationDate: Date

  constructor() {}


  get role(): string {
    return this._role;
  }

  set role(value: string) {
    this._role = value;
  }

  get userName(): string {
    return this._userName;
  }

  set userName(value: string) {
    this._userName = value;
  }

  get userProfileId(): string {
    return this._userProfileId;
  }


  set userProfileId(value: string) {
    this._userProfileId = value;
  }

  get rememberMe(): boolean {
    return this._rememberMe;
  }

  set rememberMe(value: boolean) {
    this._rememberMe = value;
  }

  get token(): string {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate) {
      return null;
    }
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }

  get tokenExpirationDate(): Date {
    return this._tokenExpirationDate;
  }

  set tokenExpirationDate(value: Date) {
    this._tokenExpirationDate = value;
  }

}
