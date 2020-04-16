package org.java.yasn.web.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import org.java.yasn.common.AuthConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);

        if(header == null || !header.startsWith(AuthConstants.AUTHORIZATION_HEADER_BEGINNING)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = this.getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey(AuthConstants.SIGNING_KEY.getBytes())
                    .parseClaimsJws(token.replace(AuthConstants.AUTHORIZATION_HEADER_BEGINNING, ""))
                    .getBody()
                    .getSubject();

            if (username != null) {

                usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null);
            }
        }

        return usernamePasswordAuthenticationToken;
    }
}
