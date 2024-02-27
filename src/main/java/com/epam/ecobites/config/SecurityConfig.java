package com.epam.ecobites.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http
          .authorizeHttpRequests(request -> request
              .requestMatchers(
                      new AntPathRequestMatcher("/h2-console/**"),
                      new AntPathRequestMatcher("/**")
              ).permitAll()
          );
        http.headers( header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }
}
