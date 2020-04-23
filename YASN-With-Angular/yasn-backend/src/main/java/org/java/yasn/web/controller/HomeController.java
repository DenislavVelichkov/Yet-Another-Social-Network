package org.java.yasn.web.controller;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.java.yasn.common.EndpointConstants;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.services.wall.WallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/home")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class HomeController extends BaseController {
  private final WallService wallService;

  @GetMapping(value = "/all-news", produces = EndpointConstants.END_POINT_PRODUCES_JSON)
  public ResponseEntity<?> allNewsFeed() {

    Collection<WallPostServiceModel> posts = this.wallService.displayAllPosts();

    return ResponseEntity.ok(posts);
  }

  /*@GetMapping("/admin")
  public ModelAndView adminPanel(ModelAndView modelAndView) {

    modelAndView.addObject(
        "userProfileView", super.getUserProfileView());
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    return super.view("admin-panel", modelAndView);
  }*/
}
