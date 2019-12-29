package org.yasn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        .cors()
        .and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeRequests()
        .antMatchers(
            "/js/**",
            "/js/script/**",
            "/images/**",
            "/css/**").permitAll()
        .antMatchers("/", "/user/register", "/user/login").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/home")
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .permitAll()
        .deleteCookies("JSESSIONID")
        .and()
        .exceptionHandling()
        .accessDeniedPage("/")
        .and()
        .sessionManagement()
        .maximumSessions(1);
  }

  private CsrfTokenRepository csrfTokenRepository() {
    HttpSessionCsrfTokenRepository repository =
        new HttpSessionCsrfTokenRepository();
    repository.setSessionAttributeName("_csrf");

    return repository;
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration()
        .applyPermitDefaultValues();
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.POST);
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    corsConfiguration.addExposedHeader(
        "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
            + "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
    source.registerCorsConfiguration("/**"
        , corsConfiguration);

    return source;
  }
}
