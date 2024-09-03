package com.telstra.billing_system.filters;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.telstra.billing_system.service.JwtService;
import com.telstra.billing_system.service.MyCustomerDetailsService;
import com.telstra.billing_system.service.MyAdminDetailsService;
import com.telstra.billing_system.service.MySupplierDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*  basically before a request reaches any relevent controller it has to go through a front controller 
 before it goes pass through that , it has to go via multiple filters
 by default it only goes through UsernamePasswordAuthenticationFilter what we are doing is making request pass via "JwtFilter" first then pass the modified request object into the UsernamePasswordAuthenticationFilter
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Qualifier("MyCustomerDetailsService")
    private MyCustomerDetailsService myCustomerDetailsService;
    @Qualifier("MyAdminDetailsService")
    private MyAdminDetailsService myAdminDetailsService;
    @Qualifier("MySupplierDetailsService")
    private MySupplierDetailsService mySupplierDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String requestURI= request.getRequestURI();
        System.out.println("Request URI: " + requestURI);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        UserDetails userDetails=null ;
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(requestURI.startsWith("/customer")) userDetails = myCustomerDetailsService.loadUserByUsername(username);
            if(requestURI.startsWith("/admin")) userDetails = myAdminDetailsService.loadUserByUsername(username);
            if(requestURI.startsWith("/service")) userDetails = mySupplierDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}