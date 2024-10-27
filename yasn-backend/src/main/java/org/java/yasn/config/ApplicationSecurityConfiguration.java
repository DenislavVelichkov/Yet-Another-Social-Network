package org.java.yasn.config;

import lombok.AllArgsConstructor;
import org.java.yasn.services.user.UserService;
import org.java.yasn.web.filters.JwtAuthenticationFilter;
import org.java.yasn.web.filters.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final CorsConfigurationSource corsConfigurationSource;
  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  // TODO: 6/17/2020 Finish the websocket security
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .cors().configurationSource(corsConfigurationSource)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("../resources/**").permitAll()
        .antMatchers(
                "/user/register",
                "/api/user/register",
                "/user/login",
                "/api/user/login",
                "/stomp/**").anonymous()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().requiresChannel()
        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userService)
        .passwordEncoder(bCryptPasswordEncoder);
  }

}
