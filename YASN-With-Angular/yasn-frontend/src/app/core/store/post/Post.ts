import {PostComment} from "./PostComment";

export interface Post {
  id: string;
  ownerFullName: string;
  ownerAvatarPictureUrl: string;
  createdOn: Date;
  content: string;
  postPicture: Array<string>;
  postPrivacy: string;
  likesCount: number;
  location: string;
  comments: PostComment[];
}


