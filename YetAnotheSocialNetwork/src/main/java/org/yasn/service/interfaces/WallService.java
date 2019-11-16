package org.yasn.service.interfaces;

import org.yasn.data.models.service.WallPostServiceModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface WallService {

  void createPost(WallPostServiceModel wallPost, Principal currentUser) throws IOException;

  List<WallPostServiceModel> displayAllPosts();

  void createComment();


}
