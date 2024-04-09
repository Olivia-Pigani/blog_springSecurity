package com.example.blog_spring_mvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInDTO {


    @Email
    @NotBlank
    private String email;

//    @NotBlank
    @Size(min = 6,max = 150)
    private String password;


}
