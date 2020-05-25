export class UserAuthModel {

  constructor(role: string,
              userProfileId: string,
              userName: string,
              rememberMe: boolean,
              token: string,
              tokenExpirationDate: Date) {
    this._role = role;
    this._userProfileId = userProfileId;
    this._userName = userName;
    this._rememberMe = rememberMe;
    this._token = token;
    this._tokenExpirationDate = tokenExpirationDate;
  }

  private _role: string

  get role(): string {
    return this._role;
  }

  set role(value: string) {
    this._role = value;
  }

  private _userProfileId: string

  get userProfileId(): string {
    return this._userProfileId;
  }

  set userProfileId(value: string) {
    this._userProfileId = value;
  }

  private _userName: string

  get userName(): string {
    return this._userName;
  }

  set userName(value: string) {
    this._userName = value;
  }

  private _rememberMe: boolean

  get rememberMe(): boolean {
    return this._rememberMe;
  }

  set rememberMe(value: boolean) {
    this._rememberMe = value;
  }

  private _token: string

  get token(): string {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate) {
      return null;
    }
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }

  private _tokenExpirationDate: Date

  get tokenExpirationDate(): Date {
    return this._tokenExpirationDate;
  }

  set tokenExpirationDate(value: Date) {
    this._tokenExpirationDate = value;
  }
}
