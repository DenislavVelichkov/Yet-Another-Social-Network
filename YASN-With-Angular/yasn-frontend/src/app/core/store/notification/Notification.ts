export interface Notification {

  isViewed: boolean;

  senderPicture: string;

  senderFullName: string;

  recipientFullName: string;

  notificationType: string;

  createdOn: Date;

  content: string;

  recipientId: string;

  senderId: string;

}
