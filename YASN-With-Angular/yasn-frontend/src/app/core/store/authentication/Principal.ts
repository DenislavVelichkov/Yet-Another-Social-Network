export interface Principal {
  id: string;
  username: string;
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  gender: string;
  birthday: string;
  isActive: string;
  createdOn: string;
  authorities: [];
  authData?: string;
  userProfileId: string;
}
