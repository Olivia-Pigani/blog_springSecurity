package com.example.blog_spring_mvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    //There is only one admin with ADMIN_ROLE in the app ( there is a singleton for it, managed by Spring, annoted with @Component, become a user and persisted in the "user" table ),
    //  but we can have many users with USER_ROLE ( managed by JPA with @Entity ).
    private RoleType roleType = RoleType.USER_ROLE;

    private boolean isEnabled=true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() { // username is equal to email
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }




}
