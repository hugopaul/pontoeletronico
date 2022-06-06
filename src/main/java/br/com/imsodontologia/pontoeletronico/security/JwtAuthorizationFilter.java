package br.com.imsodontologia.pontoeletronico.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.imsodontologia.pontoeletronico.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    private UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
       /* String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);*/

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /*private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtUtil.tokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }*/
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtUtil.getUsername(token.substring(7));

        if (token != null) {
            // parse the token.
            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (user != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user, null, userDetailsService.getAuthorities(username));
            }

            return null;
        }

        return null;
    }
}