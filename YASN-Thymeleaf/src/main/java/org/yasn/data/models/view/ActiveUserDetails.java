package org.yasn.data.models.view;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.models.view.action.NotificationViewModel;

@Getter
@Setter
@NoArgsConstructor
public class ActiveUserDetails extends BaseViewModel {

  private String firstName;
  private String profilePicture;
  private Set<NotificationViewModel> notifications;
}
