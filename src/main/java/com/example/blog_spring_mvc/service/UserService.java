package com.example.blog_spring_mvc.service;

import com.example.blog_spring_mvc.config.JwtProvider;
import com.example.blog_spring_mvc.entity.User;
import com.example.blog_spring_mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<String> saveUser(User newUser){
        if (findUserByEmail(newUser.getEmail()).isEmpty()){
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return new ResponseEntity<>("The user has been successfully saved !",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("This email already exists !",HttpStatus.NOT_ACCEPTABLE);
        }

    }

    public String generateToken(String email, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return token;
    }


    public boolean verifyUser(String email, String password) {
        return userRepository.findUserByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }



    // Spring Security method to load UserDetails by username ( aka email ).
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


    }

    // method to find if an email already exists
    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

}
