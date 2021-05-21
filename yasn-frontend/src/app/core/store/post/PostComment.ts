export interface PostComment {
  id: string;

  commentOwnerProfileId: string;

  wallPostId: string;

  avatarPictureUrl: string;

  senderFullName: string;

  createdOn: Date;

  pictures: string[];

  content: string;
}
