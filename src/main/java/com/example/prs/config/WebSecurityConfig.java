package com.example.prs.config;

import com.example.prs.service.UserService;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private  CustomUserDetailsService userService;

    public WebSecurityConfig(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager
//            (HttpSecurity httpSecurity, NoOpPasswordEncoder noOpPasswordEncoder)
    (HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/fish/**").hasRole("EMPLOYEE")
                        .requestMatchers("/people/**").hasRole("ADMIN")
                        .requestMatchers("/animal/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/sweet/**").hasAnyRole("USER")
                        .requestMatchers("/passport/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/team")
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
//    @SuppressWarnings("deprecation")
//    @Bean
//    public NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}