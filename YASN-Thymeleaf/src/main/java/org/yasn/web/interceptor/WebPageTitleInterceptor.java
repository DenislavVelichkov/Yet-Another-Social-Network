package org.yasn.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yasn.common.annotations.interceptor.PageTitle;

@Component
public class WebPageTitleInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView) throws Exception {
    String title = "YASN";

    if (modelAndView == null) {
      modelAndView = new ModelAndView();
    } else {
      if (handler instanceof HandlerMethod) {
        PageTitle pageTitle = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);

        if (pageTitle != null) {
          modelAndView
              .addObject("title",
                  title
                      + " - "
                      + pageTitle.value());
        }
      }
    }
  }
}
