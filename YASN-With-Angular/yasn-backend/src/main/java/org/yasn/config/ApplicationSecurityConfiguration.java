package org.yasn.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.yasn.validation.handlers.AuthSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final CorsConfigurationSource corsConfigurationSource;
  private final AuthSuccessHandler authSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().configurationSource(this.corsConfigurationSource)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("../resources/**").permitAll()
        .antMatchers("/user/register", "/user/login").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .usernameParameter("email")
        .passwordParameter("password")
        .loginProcessingUrl("/user/login")
        .successHandler(this.authSuccessHandler)
        .and()
        .httpBasic()
        .and()
        .logout().permitAll()
        .deleteCookies("JSESSIONID");
  }
}