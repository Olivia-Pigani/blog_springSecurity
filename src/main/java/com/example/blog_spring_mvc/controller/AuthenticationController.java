package com.example.blog_spring_mvc.controller;

import com.example.blog_spring_mvc.dto.UserSignInDTO;
import com.example.blog_spring_mvc.dto.UserSignUpDTO;
import com.example.blog_spring_mvc.entity.User;
import com.example.blog_spring_mvc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping()
    public String getAuthForm(Model model){

        model.addAttribute("userSignInDTO",new UserSignInDTO());
        return "auth-form";
    }

@PostMapping("/signup")
public ResponseEntity<String> signUp(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
    if (userService.findUserByEmail(userSignUpDTO.getEmail()).isPresent()) {
        return new ResponseEntity<>("this email already exists", HttpStatus.BAD_REQUEST);
    }

    User user = User.builder()
            .name(userSignUpDTO.getName())
            .email(userSignUpDTO.getEmail())
            .password(passwordEncoder.encode(userSignUpDTO.getPassword()))
            .build();

    userService.saveUser(user);

    return new ResponseEntity<>("success, this user is now in our database !", HttpStatus.OK);

}
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody UserSignInDTO userSignInDTO) {
        String token = userService.generateToken(userSignInDTO.getEmail(), userSignInDTO.getPassword());
        if (token == null) {
            return new ResponseEntity<>("there is an error about email and/or password !", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);
    }
// no sign out in here, we'll manage this one in thymeleaf html files






}
