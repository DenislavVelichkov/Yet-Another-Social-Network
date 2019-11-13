package org.yasn.service.interfaces;

import org.yasn.data.models.service.WallPostServiceModel;
import org.yasn.data.models.view.WallPostViewModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface WallService {
  WallPostServiceModel createPost(WallPostServiceModel wallPost, Principal currentUser) throws IOException;

  List<WallPostViewModel> displayAllPosts();
}
