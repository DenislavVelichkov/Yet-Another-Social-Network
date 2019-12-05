package org.yasn.data.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActiveUserDetails extends BaseViewModel{

  private String firstName;
  private String profilePicture;
  private Set<NotificationViewModel> notifications;
}
