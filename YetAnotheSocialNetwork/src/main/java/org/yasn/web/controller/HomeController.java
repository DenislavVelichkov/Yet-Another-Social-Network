package org.yasn.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.PageTitle;

@Controller
public class HomeController extends BaseController {


  @GetMapping("/home")
  @PreAuthorize("isAuthenticated()")
  @PageTitle("Home")
  public ModelAndView home(ModelAndView modelAndView) {
    return super.view("home", modelAndView);
  }
}
