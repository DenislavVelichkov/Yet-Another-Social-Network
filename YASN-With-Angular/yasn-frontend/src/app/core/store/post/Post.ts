import {Timestamp} from "rxjs";

export class Post {
  private postPicture: string;
  private postContent: string;
  private comments: [];
  private createdOn: Timestamp<Date>;
  private postPrivacy: string;
  private actualLikes: [];
  private location: string;
}
