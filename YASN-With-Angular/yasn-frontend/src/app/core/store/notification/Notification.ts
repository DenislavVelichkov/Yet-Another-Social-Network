export interface Notification {

  isViewed: boolean;

  senderPicture: string;

  senderFullName: string;
  //change the property to NotificationType enum
  notificationType: string;

  createdOn: Date;

  content: string;

}
