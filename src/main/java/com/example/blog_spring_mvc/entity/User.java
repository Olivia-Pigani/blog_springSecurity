package com.example.blog_spring_mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true,nullable = false)
    private String email;

    @Size(min = 6,max = 150)
//    @NotBlank
    private String password;

    //There is only one admin with ADMIN_ROLE in the app ( there is a singleton for it, managed by Spring, annoted with @Component, become a user and persisted in the "user" table ),
    //  but we can have many users with USER_ROLE ( managed by JPA with @Entity ).
    private RoleType roleType = RoleType.USER_ROLE;

    private boolean isEnabled=true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(roleType.name()));
    }
    @Override
    public String getUsername() { // username is equal to email
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }




}
