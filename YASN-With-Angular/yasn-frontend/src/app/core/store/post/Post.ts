import {Timestamp} from "rxjs";

export class Post {
  private _id: string;
  private _ownerFullName: string;
  private _ownerAvatarPictureUrl: string;
  private _createdOn: Timestamp<Date>;
  private _content: string;
  private _postPrivacy: string;
  private _likesCount: number;
  private _location: string;


  constructor(id: string,
              ownerFullName: string,
              ownerAvatarPictureUrl: string,
              createdOn: Timestamp<Date>,
              content: string,
              postPrivacy: string,
              likesCount: number,
              location: string) {
    this._id = id;
    this._ownerFullName = ownerFullName;
    this._ownerAvatarPictureUrl = ownerAvatarPictureUrl;
    this._createdOn = createdOn;
    this._content = content;
    this._postPrivacy = postPrivacy;
    this._likesCount = likesCount;
    this._location = location;
  }

  set id(value: string) {
    this._id = value;
  }

  set ownerFullName(value: string) {
    this._ownerFullName = value;
  }

  set ownerAvatarPictureUrl(value: string) {
    this._ownerAvatarPictureUrl = value;
  }

  set createdOn(value: Timestamp<Date>) {
    this._createdOn = value;
  }

  set content(value: string) {
    this._content = value;
  }

  set postPrivacy(value: string) {
    this._postPrivacy = value;
  }

  set likesCount(value: number) {
    this._likesCount = value;
  }

  set location(value: string) {
    this._location = value;
  }

  get id(): string {
    return this._id;
  }

  get ownerFullName(): string {
    return this._ownerFullName;
  }

  get ownerAvatarPictureUrl(): string {
    return this._ownerAvatarPictureUrl;
  }

  get createdOn(): Timestamp<Date> {
    return this._createdOn;
  }

  get content(): string {
    return this._content;
  }

  get postPrivacy(): string {
    return this._postPrivacy;
  }

  get likesCount(): number {
    return this._likesCount;
  }

  get location(): string {
    return this._location;
  }
}
