package com.example.seguridad.jwt;

import java.io.IOException;
//import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.seguridad.services.JwtService;

@Component
public class JwtAutheticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * String header = request.getHeader("Authorization");
         * if (header != null && header.startsWith("Bearer ")) {
         * String token = header.substring(7);
         */
        try {
            username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()/* new ArrayList<>() */);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    

    private String getTokenFromRequest(HttpServletRequest request) {
        // primero se busca del encabezado el item/propiedad de autenticacion
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // si existe y comienza con Bearer entonces a continuacion de esta palabra viene
        // el token para extraer
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {// a partir del caracter 7 hasta el
                                                                                 // final es el token caso contrario
                                                                                 // retorna null
            return authHeader.substring(7);
        }
        return null;
    }

}
