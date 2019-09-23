package org.yasn.web.controller;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

  public BaseController() {
  }

  public ModelAndView view(String viewName, ModelAndView modelAndView) {
    modelAndView.setViewName(viewName);

    return modelAndView;
  }

  public ModelAndView view(String viewName) {
    return this.view(viewName, new ModelAndView());
  }

  public ModelAndView redirect(String URL) {
    return this.view("redirect:" + URL);
  }
}
