package com.project.user.config;

import com.project.user.entity.User;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private DataSource securityDataSource;

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register", "/login", "/blocked").permitAll()
                                .requestMatchers("/user/**", "/checkout").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/user/home", true)
                                .failureUrl("/login?error")
                                .permitAll()
                                .successHandler((request, response, authentication) -> {
                                    String username = authentication.getName();
                                    User user = userService.findByEmail(username);
                                    if (user.getIsBlocked()) {
                                        response.sendRedirect("/blocked");
                                    } else {
                                        response.sendRedirect("/user/home");
                                    }
                                })
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/accessDenied")
                )
                .csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
        jdbcDaoImpl.setDataSource(securityDataSource);
        jdbcDaoImpl.setUsersByUsernameQuery("select email as username, password, true as enabled from User where email = ?");
        jdbcDaoImpl.setAuthoritiesByUsernameQuery("select email as username, 'ROLE_USER' from User where email = ?");
        return jdbcDaoImpl;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
