package com.example.blog_spring_mvc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInDTO {

    private String email;
    private String password;


}
