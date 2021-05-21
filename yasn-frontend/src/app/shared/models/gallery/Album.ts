import {Picture} from "./Picture";

export interface Album {

  id: string,

  picture: Array<Picture>,

  name: string,

  cover: string,
}
