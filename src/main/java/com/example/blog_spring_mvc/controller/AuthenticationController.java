package com.example.blog_spring_mvc.controller;

import com.example.blog_spring_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {


    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/auth-form")
//    public String getAuthForm(Model model) {
////        model.addAttribute()
//        return "auth-form";
//    }
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> registerUser(@RequestBody User newUser){
//       return userService.saveUser(newUser);
//    }
//
//    @PostMapping("/signin")
//    public String signIn(@ModelAttribute("userSignInDTO") UserSignInDTO userSignInDTO, Model model, HttpSession session) {
//        if (userService.findUserByEmail(userSignInDTO.getEmail()).isPresent()) {
//            if (userService.verifyUser(userSignInDTO.getEmail(), userSignInDTO.getPassword())) {
//                Map<String, Object> data = new HashMap<>();
//                data.put("token", userService.generateToken(userSignInDTO.getEmail(), userSignInDTO.getPassword()));
//                new BaseResponseDTO("success", data);
//                session.setAttribute("isLogged", true);
//                return "redirect:/";
//            } else {
//                new BaseResponseDTO("wrong password");
//                return "redirect:/";
//            }
//
//        } else {
//            new BaseResponseDTO("user not exist");
//            session.setAttribute("isLogged", false);
//            return "auth-form";
//        }
//
//    }
//
//    @GetMapping("signout")
//    public String signOut(HttpSession session) { // still keep HttpSession
//        session.setAttribute("isLogged", false);
//        return "redirect:/";
//    }
//






}
