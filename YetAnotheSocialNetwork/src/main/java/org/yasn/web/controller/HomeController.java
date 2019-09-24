package org.yasn.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.yasn.common.annotations.PageTitle;
import org.yasn.domain.models.binding.UserRegisterBindingModel;

@Controller
public class HomeController extends BaseController {

  @GetMapping("/")
  @PreAuthorize("isAnonymous()")
  @PageTitle("Index")
  public ModelAndView index(ModelAndView modelAndView, @ModelAttribute(name = "model") UserRegisterBindingModel model) {
    modelAndView.addObject("model", model);
    return super.view("index");
  }
}
