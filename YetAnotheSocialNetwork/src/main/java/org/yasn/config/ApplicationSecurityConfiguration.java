package org.yasn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {

    web.ignoring().antMatchers("/css/**", "/js/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .cors().disable()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeRequests()
        .antMatchers(
            "/js/**",
            "/js/script/**",
            "/images/**", "/css/**",
            "/public/resources/**").permitAll()
        .antMatchers("/", "/user/register", "/user/login").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/user/login")
        .loginPage("/user/register")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/home")
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/")
        .and()
        .sessionManagement()
        .maximumSessions(1)
        .expiredUrl("/");
  }

  private CsrfTokenRepository csrfTokenRepository() {
    HttpSessionCsrfTokenRepository repository =
        new HttpSessionCsrfTokenRepository();
    repository.setSessionAttributeName("_csrf");

    return repository;
  }
}
