package org.yasn.utils;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtilImpl implements TimeUtil {
  private static final String DELIMITER = "ago";

  public TimeUtilImpl() {
  }

  @Override
  public String calcTimeAgo(Timestamp eventTime) {

    long timeAgo = new Date().getTime() - eventTime.getTime();

    int seconds = (int) (timeAgo / 1000) % 60;
    int minutes = (int) ((timeAgo / (1000 * 60)) % 60);
    int hours = (int) ((timeAgo / (1000 * 60 * 60)) % 24);
    //    int days = (int) (timeAgo / (1000 * 60 * 60 * 24));
    int days = (int) ((timeAgo / (1000 * 60 * 60 * 24)) % 7);
    int weeks = (int) (timeAgo / (1000 * 60 * 60 * 24 * 7));

    StringBuilder sb = new StringBuilder();

    if (weeks > 0) {
      if (weeks == 1) {
        sb.append(weeks).append(" week ");
      } else {
        sb.append(weeks).append(" weeks ");
      }
      sb.append(DELIMITER);
    } else if (days > 0) {
      if (days == 1) {
        sb.append(days).append(" day ");
      } else {
        sb.append(days).append(" days ");
      }
      sb.append(DELIMITER);
    } else if (hours > 0) {
      if (hours == 1) {
        sb.append(hours).append(" hour ");
      } else {
        sb.append(hours).append(" hours ");
      }
      sb.append(DELIMITER);
    } else if (minutes > 0) {
      if (minutes == 1) {
        sb.append(minutes).append(" minute ");
      } else {
        sb.append(minutes).append(" minutes ");
      }
      sb.append(DELIMITER);
    } else if (seconds > 0) {
      sb.append(seconds).append(" seconds ").append(DELIMITER);
    } else {
      sb.append("Just now");
    }

    return sb.toString();
  }

}
