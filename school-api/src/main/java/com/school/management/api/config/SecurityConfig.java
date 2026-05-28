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

                        // PUBLIC
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // SUPER ADMIN
                        .requestMatchers("/api/v1/school/**")
                        .hasAuthority(Constants.ROLE_SUPER_ADMIN)

                        // =========================
                        // ADMIN ONLY ROUTES FIRST
                        // =========================

                        .requestMatchers("/api/v1/academic-year/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/address/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/admission/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/class-subject/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/time-table/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/fee-structures/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/parents/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/school-class/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/student/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/subject/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/teacher-class-subject/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        .requestMatchers("/api/v1/teacher/admin/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_SUPER_ADMIN)

                        // =========================
                        // SHARED ROUTES
                        // =========================

                        .requestMatchers("/api/v1/academic-year/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/address/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/admission/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/attendance/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/class-subject/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/time-table/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/exam/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/exam-subjects/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/fee-payments/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/fee-structures/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/parents/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER,
                                Constants.ROLE_PARENT)

                        .requestMatchers("/api/v1/school-class/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/student/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/student-fees/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/student-marks/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/subject/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/teacher-class-subject/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/teacher/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_SUPER_ADMIN,
                                Constants.ROLE_TEACHER)

                        .requestMatchers("/api/v1/user/**")
                        .hasAnyAuthority(Constants.ROLE_ADMIN,
                                Constants.ROLE_TEACHER,
                                Constants.ROLE_SUPER_ADMIN)

                        .anyRequest().authenticated()
                )
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