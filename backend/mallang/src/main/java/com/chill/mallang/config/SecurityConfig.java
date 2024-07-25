package com.chill.mallang.config;

import com.chill.mallang.domain.user.jwt.JWTFilter;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.jwt.JoinFilter;
import com.chill.mallang.domain.user.jwt.LoginFilter;
import com.chill.mallang.domain.user.oauth.CustomOAuthProvider;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.chill.mallang.domain.user.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuthProvider customOAuthProvider;
    private final JoinService joinService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService, CustomOAuthProvider customOAuthProvider, JoinService joinService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customOAuthProvider = customOAuthProvider;
        this.joinService = joinService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("swagger-ui/**", "api-docs/**", "/api/v1/user/login", "/api/v1/user/join", "login", "/")
                        .permitAll()
                        .anyRequest().authenticated())
                    .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JoinFilter(joinService), UsernamePasswordAuthenticationFilter.class)
                    .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customOAuthProvider);
    }
}
