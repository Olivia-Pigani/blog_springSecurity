package com.example.blog_spring_mvc.dto;

import com.example.blog_spring_mvc.entity.RoleType;
import jakarta.persistence.Column;

import java.util.UUID;

public class UserSignUpDTO {
    private String name;
    private String email;
    private String password;

}
