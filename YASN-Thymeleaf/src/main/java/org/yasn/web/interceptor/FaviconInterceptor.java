package org.yasn.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView) throws Exception {
    String link =
        "https://res.cloudinary.com/yet-another-social-network/image/"
            + "upload/v1569504497/yet-another-social-network/fav-icon2_ruhynr.jpg";

    if (modelAndView != null) {
      modelAndView.addObject("favicon", link);
    }
  }
}
