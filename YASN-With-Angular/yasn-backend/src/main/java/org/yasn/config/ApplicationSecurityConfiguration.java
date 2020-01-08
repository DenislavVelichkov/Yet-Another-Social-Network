package org.yasn.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] CSRF_IGNORE = {"/user/login/**", "/user/register/**"};
  private final CorsConfigurationSource corsConfigurationSource;
  private final CsrfTokenRepository csrfTokenRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .cors().configurationSource(this.corsConfigurationSource)
        .and()
        .csrf().ignoringAntMatchers(CSRF_IGNORE)
        .csrfTokenRepository(this.csrfTokenRepository)
        .and()
        .authorizeRequests()
        .antMatchers("../resources/**").permitAll()
        .antMatchers("/index.html", "/user/register", "/user/login").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl("/user/login")
        .usernameParameter("email")
        .passwordParameter("password")
        .and()
        .sessionManagement()
        .and()
        .logout()
        .deleteCookies("JSESSIONID")
        .permitAll()
        .and()
        .exceptionHandling();
  }
}
