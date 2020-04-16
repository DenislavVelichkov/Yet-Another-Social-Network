import {Principal} from "./Principal";

class UserAuthModel implements Principal{

  constructor(
    public userProfileId: string,
    public userName: string,
    public role: string,
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
