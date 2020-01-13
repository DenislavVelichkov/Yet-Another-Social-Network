package org.java.yasn.web.controller;

import lombok.AllArgsConstructor;
import org.java.yasn.services.wall.WallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class HomeController extends BaseController {
  private final WallService wallService;

  @GetMapping("/home")
  public ResponseEntity<?> home() {


    return ResponseEntity.ok(this.wallService.displayAllPosts());
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
