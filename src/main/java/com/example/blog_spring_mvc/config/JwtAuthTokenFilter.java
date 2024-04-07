package com.example.blog_spring_mvc.config;

import com.example.blog_spring_mvc.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final UserService userService;

    @Autowired
    public JwtAuthTokenFilter(JwtProvider jwtProvider, JwtAuthEntryPoint jwtAuthEntryPoint, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.userService = userService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJWTFromRequest(request);

            if (token != null && jwtProvider.validateToken(token)) {

                String username = jwtProvider.getUsernameFromToken(token);

                UserDetails userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            jwtAuthEntryPoint.commence(request, response, e);
        }

    }




    private String getJWTFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
