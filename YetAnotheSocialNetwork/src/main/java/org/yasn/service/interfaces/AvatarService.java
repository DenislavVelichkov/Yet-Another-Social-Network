package org.yasn.service.interfaces;

import org.yasn.data.models.view.AvatarViewModel;

public interface AvatarService {
  AvatarViewModel findAvatarByOwnerId(String id);
}
