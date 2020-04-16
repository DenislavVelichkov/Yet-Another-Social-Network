export class UserAuthModel {

   private _role: string
   private _userProfileId: string
   private _userName: string
   private _rememberMe: boolean
   private _token: string
   private _tokenExpirationDate: Date


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

  get token(): string {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate) {
      return null;
    }
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }


  get role(): string {
    return this._role;
  }

  set role(value: string) {
    this._role = value;
  }

  get userProfileId(): string {
    return this._userProfileId;
  }

  set userProfileId(value: string) {
    this._userProfileId = value;
  }

  get userName(): string {
    return this._userName;
  }

  set userName(value: string) {
    this._userName = value;
  }

  get rememberMe(): boolean {
    return this._rememberMe;
  }

  set rememberMe(value: boolean) {
    this._rememberMe = value;
  }

  get tokenExpirationDate(): Date {
    return this._tokenExpirationDate;
  }

  set tokenExpirationDate(value: Date) {
    this._tokenExpirationDate = value;
  }
}
