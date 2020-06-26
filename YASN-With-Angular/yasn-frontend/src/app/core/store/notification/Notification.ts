export interface Notification {

  isViewed: boolean;

  senderPicture: string;

  senderFullName: string;

  notificationType: string;

  createdOn: Date;

  content: string;

  recipientId: string;

  action: string;

}
