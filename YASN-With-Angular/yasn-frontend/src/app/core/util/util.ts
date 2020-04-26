//Needed for NgRx Action types categorization

const typeCache: { [label: string]: boolean } = {};

export function type<T>(label: T | ""): T {
  if (typeCache[<string>label]) {
    throw new Error(`Action type "${label}" is not unique"`);
  }

  typeCache[<string>label] = true;

  return <T>label;
}

export function timeConverter(time: Date): string {
  const delimiter: string = "ago";
  const elapsedTime: number = new Date().getTime() - new Date(time).getTime();
  let result = "";

  const seconds: number = Number.parseInt(((elapsedTime / 1000) % 60).toFixed(0));
  const minutes: number = Number.parseInt(((elapsedTime / (1000 * 60)) % 60).toFixed(0));
  const hours: number = Number.parseInt(((elapsedTime / (1000 * 60 * 60)) % 24).toFixed(0));
  //    int days = (int) (timeAgo / (1000 * 60 * 60 * 24));
  const days: number = Number.parseInt(((elapsedTime / (1000 * 60 * 60 * 24)) % 7).toFixed(0));
  const weeks: number = Number.parseInt((elapsedTime / (1000 * 60 * 60 * 24 * 7)).toFixed(0));

  if (weeks > 0) {
    if (weeks == 1) {
      result += weeks + " week ";
    } else {
      result += weeks + " weeks ";
    }
    result += delimiter;
  } else if (days > 0) {
    if (days == 1) {
      result += days + " day ";
    } else {
      result += days + " days ";
    }
    result += delimiter;
  } else if (hours > 0) {
    if (hours == 1) {
      result += hours + " hour ";
    } else {
      result += hours + " hours ";
    }
    result += delimiter;
  } else if (minutes > 0) {
    if (minutes == 1) {
      result += minutes + " minute ";
    } else {
      result += minutes + " minutes ";
    }
    result += delimiter;
  } else if (seconds > 0) {
    result += minutes + " minute " + delimiter;
  } else {
    result += "Just now";
  }

  return result;
}
