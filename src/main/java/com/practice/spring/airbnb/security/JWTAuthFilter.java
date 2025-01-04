package com.practice.spring.airbnb.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

        private final JWTService jwtService;
        private final UserService userService;

        @Autowired
        @Qualifier("handlerExceptionResolver")
        private HandlerExceptionResolver handlerExceptionResolver;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {
                try {
                        final String requestTokenHeader = request.getHeader("Authorization");

                        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                                filterChain.doFilter(request, response);
                                return;
                        }

                        String token = requestTokenHeader.split("Bearer")[1];
                        Long userId = jwtService.getUserIdFromToken(token);

                        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                                User user = userService.getUserId(userId);
                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                                user, null, user.getAuthorities());
                                authenticationToken.setDetails(
                                                new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                        filterChain.doFilter(request, response);

                } catch (JwtException exception) {
                        handlerExceptionResolver.resolveException(request, response, filterChain, exception);
                }catch(Exception e){
                        handlerExceptionResolver.resolveException(request, response, filterChain, e);
                }
        }
}
