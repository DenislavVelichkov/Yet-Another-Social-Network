import {Timestamp} from "rxjs";

export class Post {
  private postPicture: string;
  private postContent: string;
  private comments: [];
  private createdOn: Timestamp<Date>;
  private postPrivacy: string;
  private actualLikes: [];
  private location: string;


  constructor(postPicture: string, postContent: string, comments: [], createdOn: Timestamp<Date>, postPrivacy: string, actualLikes: [], location: string) {
    this.postPicture = postPicture;
    this.postContent = postContent;
    this.comments = comments;
    this.createdOn = createdOn;
    this.postPrivacy = postPrivacy;
    this.actualLikes = actualLikes;
    this.location = location;
  }
}
