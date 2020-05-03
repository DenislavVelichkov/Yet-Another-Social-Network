export interface Notification {

  isViewed: boolean;

  recipientPicture: string;

  recipientFullName: string;
  //change the property to NotificationType enum
  notificationType: string;

  createdOn: Date;

  content: string;

}
