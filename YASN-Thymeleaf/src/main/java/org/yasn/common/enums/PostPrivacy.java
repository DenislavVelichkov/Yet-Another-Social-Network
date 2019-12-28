package org.yasn.common.enums;

public enum PostPrivacy {
  PUBLIC("Public"),
  FRIENDS_ONLY("Friends only");

  //  private static final Map<String, PostPrivacy> LABEL_TO_CLASS_MAP =
  //      Stream.of(PostPrivacy.values())
  //      .collect(Collectors.toUnmodifiableMap(
  //          PostPrivacy::getLabel, postPrivacy -> postPrivacy));

  private final String label;

  PostPrivacy(String label) {
    this.label = label;
  }

  //  public static PostPrivacy fromLabel(String label) {
  //    return label == null ? null : LABEL_TO_CLASS_MAP.get(label);
  //  }

  public String getLabel() {
    return this.label;
  }
}
