package org.java.yasn.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.java.yasn.common.AuthConstants;
import org.java.yasn.common.AuthorityConstants;
import org.java.yasn.common.ErrorConstants;
import org.java.yasn.data.entities.user.Role;
import org.java.yasn.data.entities.user.User;
import org.java.yasn.web.models.binding.UserLoginModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginModel loginBindingModel = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginModel.class);

            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginBindingModel.getEmail(),
                            loginBindingModel.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException ignored) {
            return  null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = ((User) authResult.getPrincipal());
//        String authority = extractHighestAuthorityFromAuthorities(user.getAuthorities());
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(new Date().getTime() + 864000000L))
                .claim("role", "blank")
                .claim("userId", user.getId())
                .signWith(SignatureAlgorithm.HS256, AuthConstants.SIGNING_KEY.getBytes())
                .compact();

        response.addHeader(AuthConstants.AUTHORIZATION_HEADER, AuthConstants.AUTHORIZATION_HEADER_BEGINNING + token);
    }

    private String extractHighestAuthorityFromAuthorities(Set<Role> authorities) {

        Set<String> allAuthoritiesAsString = authorities
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());

        if (allAuthoritiesAsString.contains(AuthorityConstants.AUTHORITY_ROOT_ADMIN)) {
            return AuthorityConstants.AUTHORITY_ROOT_ADMIN;
        } else if (allAuthoritiesAsString.contains(AuthorityConstants.AUTHORITY_ADMIN)) {
            return AuthorityConstants.AUTHORITY_ADMIN;
        } else if (allAuthoritiesAsString.contains(AuthorityConstants.AUTHORITY_MODERATOR)) {
            return AuthorityConstants.AUTHORITY_MODERATOR;
        } else if (allAuthoritiesAsString.contains(AuthorityConstants.AUTHORITY_USER)) {
            return AuthorityConstants.AUTHORITY_USER;
        } else {
            throw new IllegalArgumentException(ErrorConstants.NO_SUCH_ROLE);
        }
    }
}
