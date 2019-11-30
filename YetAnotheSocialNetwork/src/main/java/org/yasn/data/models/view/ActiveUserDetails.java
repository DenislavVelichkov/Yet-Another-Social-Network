package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActiveUserDetails {
  private String id;
  private String firstName;
  private String profilePicture;
}
