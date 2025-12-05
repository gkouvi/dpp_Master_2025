package gr.uoi.dit.master2025.gkouvas.dpp.security.config;

import gr.uoi.dit.master2025.gkouvas.dpp.security.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtFilter) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/sites/**", "/device/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR")
                        .requestMatchers("/maintenance/**").permitAll()//hasAnyRole("TECHNICIAN", "SUPERVISOR", "ADMIN")
                        .requestMatchers("/documents/**").permitAll()//hasAnyRole("SUPERVISOR", "ADMIN")
                        .requestMatchers("/alerts/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR", "TECHNICIAN")
                        .requestMatchers("/buildings/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR")
                        .requestMatchers("/devices/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR")
                        .requestMatchers("/devices/health").permitAll()
                        .requestMatchers("/environment/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR","TECHNICIAN")
                        .requestMatchers("/dpp/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR","TECHNICIAN")
                        .requestMatchers("/analytics/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR","TECHNICIAN")
                        .requestMatchers("/monitoring/**").permitAll()//hasAnyRole("ADMIN", "SUPERVISOR","TECHNICIAN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
