package com.example.blog_spring_mvc.dto;

import com.example.blog_spring_mvc.entity.RoleType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserSignUpDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6,max = 150)
    private String password;

}
