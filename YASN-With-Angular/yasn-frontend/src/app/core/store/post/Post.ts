import {Timestamp} from "rxjs";

export interface Post {
  id: string;
  ownerFullName: string;
  ownerAvatarPictureUrl: string;
  createdOn: Timestamp<Date>;
  content: string;
  postPrivacy: string;
  likesCount: number;
  location: string;
}


