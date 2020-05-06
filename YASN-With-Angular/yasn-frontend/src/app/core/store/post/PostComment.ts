export interface PostComment {
  id: string;

  wallPostId: string;

  avatarPictureUrl: string;

  senderFullName: string;

  createdOn: Date;

  pictures: string[];

  content: string;
}
