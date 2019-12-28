package org.yasn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yasn.web.interceptor.FaviconInterceptor;
import org.yasn.web.interceptor.WebPageTitleInterceptor;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

  private final WebPageTitleInterceptor titleInterceptor;
  private final FaviconInterceptor faviconInterceptor;

  @Autowired
  public ApplicationWebMvcConfiguration(WebPageTitleInterceptor titleInterceptor,
                                        FaviconInterceptor faviconInterceptor) {
    this.titleInterceptor = titleInterceptor;
    this.faviconInterceptor = faviconInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.titleInterceptor);
    registry.addInterceptor(this.faviconInterceptor);
  }
}
