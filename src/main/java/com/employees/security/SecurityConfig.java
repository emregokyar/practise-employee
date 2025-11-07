package com.employees.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig {
    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails armes = User.builder()
                .username("ana")
                .password("{noop}123")
                .roles("EMPLOYEE")
                .build();

        UserDetails sydney = User.builder()
                .username("sydney")
                .password("{noop}123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails emre = User.builder()
                .username("emre")
                .password("{noop}123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(emre, sydney, armes);
    }
    */

    // Pulling user details info from db
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // new JdbcUserDetailsManager(dataSource);// this works only in db, table names are users(username, password) and authorities(username, authority)

        // Basic custom jdbc user details manager
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configuration ->
                configuration
                        .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll() // H2 DB config
                        .requestMatchers(HttpMethod.POST, "/h2-console/**").permitAll() // H2 DB config
                        .requestMatchers("/docs/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll() // SWAGGER config
                        .requestMatchers(HttpMethod.GET, "/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/employees/**").hasRole("ADMIN")
        );

        // httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        // Using default http sec
        httpSecurity.httpBasic(Customizer.withDefaults());
        // Disabling CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // Using customized exception handling
        httpSecurity.exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint()));

        // Disabling header options to use frame because h2 db uses frames for secured endpoints
        httpSecurity.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.build();
    }

    // Handling unauthorized access
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            // Send an unauthorized status as a JSON value if auth is fail as a code
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            // Removes WWW authenticate which is browser pop up
            response.setHeader("WWW-Authenticate", "");
            // Return the error which is present - return this message
            response.getWriter().write("{\"error\":\"Unauthorized access\"}");
        });
    }
}
