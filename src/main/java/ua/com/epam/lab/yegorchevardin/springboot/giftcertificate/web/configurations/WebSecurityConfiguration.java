package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.configurations.jwt.JwtRequestFilter;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.constants.AccessPoints;

/**
 * Application auth configuration class
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers(AccessPoints
                                .getAccessPointsArray()).permitAll()
                        .requestMatchers(HttpHeaders.ALLOW).permitAll()
                        .requestMatchers(HttpMethod.GET, AccessPoints.getGetAccessPoints()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/gift-certificates/**").permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
