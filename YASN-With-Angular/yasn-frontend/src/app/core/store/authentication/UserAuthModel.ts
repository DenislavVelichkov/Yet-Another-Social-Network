import {Principal} from "./Principal";

class UserAuthModel implements Principal{

  constructor(
   public role: string,
   public userProfileId: string,
   public fullName: string,
   public avatarUrl: string,
   public coverPictureUrl: string,
   public rememberMe: boolean,
   public _token: string,
   public tokenExpirationDate: Date,

  ) {}

  get token() {
    if (!this.tokenExpirationDate || new Date() > this.tokenExpirationDate) {
      return null;
    }
    return this._token;
  }


  set token(value: string) {
    this._token = value;
  }
}
