package com.example.elasticqueryservice.config;

import com.example.appconfigdata.UserConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserConfigData userConfigData;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/**").hasRole("USER")
                            .anyRequest().permitAll()
                ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    // Or use this way
    @Bean
    public InMemoryUserDetailsManager userDetailService() {
        UserDetails user = User.withUsername(userConfigData.getUsername())
                .password(passwordEncoder().encode(userConfigData.getPassword()))
                //.password("{noop}yourpassword) indicate that do not use password end coding
                .roles(userConfigData.getRoles())
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
