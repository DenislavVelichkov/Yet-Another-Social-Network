package org.yasn.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.interceptor.PageTitle;
import org.yasn.data.models.binding.PostCommentBindingModel;
import org.yasn.data.models.binding.WallPostBindingModel;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

  @GetMapping("/")
  public ModelAndView index() {

    return super.redirect("/user/register");
  }

  @GetMapping("/home")
  @PageTitle("Home")
  public ModelAndView home(
      ModelAndView modelAndView,
      @ModelAttribute(name = "wallPost") WallPostBindingModel wallPost,
      @ModelAttribute(name = "postComment") PostCommentBindingModel postComment) {


    // TODO: 11/14/2019 Optimize display with some kind of Cache method

    modelAndView.addObject("userProfileView", super.getUserProfileView());
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    modelAndView.addObject("allWallPosts", super.getAllPosts());
    return super.view("home", modelAndView);
  }

  @GetMapping("/admin")
  public ModelAndView adminPanel(ModelAndView modelAndView) {

    modelAndView.addObject(
        "userProfileView", super.getUserProfileView());
    modelAndView.addObject(
        "activeUserDetails", super.getActiveUserDetails());
    return super.view("admin-panel", modelAndView);
  }
}
