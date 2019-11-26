package org.yasn.web.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

@NoArgsConstructor
public abstract class BaseController {

  protected ModelAndView view(String viewName, ModelAndView modelAndView) {
    modelAndView.setViewName(viewName);

    return modelAndView;
  }

  protected ModelAndView view(String viewName) {
    return this.view(viewName, new ModelAndView());
  }

  protected ModelAndView redirect(String URL) {
    return this.view("redirect:" + URL);
  }
}
