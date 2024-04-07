package com.example.blog_spring_mvc.security;

import com.example.blog_spring_mvc.config.JwtAuthEntryPoint;
import com.example.blog_spring_mvc.config.JwtAuthTokenFilter;
import com.example.blog_spring_mvc.config.JwtProvider;
import com.example.blog_spring_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// no CORS management because there is just the Spring app.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SecurityConfig(UserService userService, JwtProvider jwtProvider, JwtAuthEntryPoint jwtAuthEntryPoint, AuthenticationConfiguration authenticationConfiguration) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.authenticationConfiguration = authenticationConfiguration;
    }



    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }


    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() {
        return new JwtAuthTokenFilter( jwtProvider, jwtAuthEntryPoint, userService);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disabled CSRF (Cross-Site Request Forgery) for REST, because we use tokens over cookies.
                .csrf(csrf -> csrf.disable())


                // Requests are stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/","/details/{postId}","/auth-form","/signin").permitAll()
                        .requestMatchers("/com-form/{postId}","/addCommentary/{postId}").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/post-form/**","/addOrUpdateAPost/","/deletepost/**").hasRole("ADMIN")
                        .anyRequest().authenticated())


                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthEntryPoint))


                .addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }






}
