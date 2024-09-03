package com.telstra.billing_system.config;

import com.telstra.billing_system.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // telling spring boss this is a configuration class use for that
@EnableWebSecurity // telling spring go with the config mentioned below not the default one
public class SecurityConfig {
    @Qualifier("MyUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // return http.build(); just doing this is like disabling all the filters it as good as not having spring sec

        // diable csrf
        http.csrf(customizer->customizer.disable());
        http.authorizeHttpRequests(request->request
                                                .requestMatchers("/customer/register", "/customer/login", "/supplier/login", "/supplier/register","/admin/login")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated());
        http.httpBasic(Customizer.withDefaults()); // for postman

        // as we have disabled csrf we need to make each req a new session to enhance sec by making each session stateless
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
        
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // need the URL
        System.out.println(userDetailsService);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
