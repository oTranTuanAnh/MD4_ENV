package com.example.demo.config;

import com.example.demo.service.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class AppSecConfig {
    @Autowired
    private AppUserService userService;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->{
            authorize
                    .requestMatchers("/home").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/user").hasRole("USER");
        }).formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider passwordEncoder(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return  authenticationProvider;
    }

}
