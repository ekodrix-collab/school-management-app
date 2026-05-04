package com.school.management.api.config;

import com.school.management.api.constants.Constants;
import com.school.management.api.service.authService.CustomUserDetailsService;
import com.school.management.api.security.JwtAuthFilter;
import com.school.management.api.security.SecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final SecurityExceptionHandler securityExceptionHandler;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(securityExceptionHandler)
                        .accessDeniedHandler(securityExceptionHandler))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/school/create").hasAuthority(Constants.ROLE_SUPER_ADMIN)
                        .requestMatchers("/api/v1/school-class/create").hasAnyAuthority(Constants.ROLE_TEACHER,Constants.ROLE_ADMIN)
                        .requestMatchers("/api/v1/user/create").hasAnyAuthority(Constants.ROLE_ADMIN,Constants.ROLE_TEACHER)
                        .requestMatchers("/api/v1/admin/**").hasAuthority(Constants.ROLE_ADMIN)
                        .requestMatchers("/api/v1/attendance/**").hasAnyAuthority(Constants.ROLE_ADMIN,Constants.ROLE_TEACHER)
                        .requestMatchers("/api/v1/teacher/**").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_TEACHER)
                        .requestMatchers("/api/v1/student/**")
                        .hasAnyAuthority(Constants.ROLE_SUPER_ADMIN, Constants.ROLE_ADMIN,Constants.ROLE_PARENT,Constants.ROLE_TEACHER)
                        .anyRequest().permitAll())

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}