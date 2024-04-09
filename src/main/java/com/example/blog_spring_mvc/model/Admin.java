package com.example.blog_spring_mvc.model;


import com.example.blog_spring_mvc.entity.RoleType;
import com.example.blog_spring_mvc.entity.User;
import com.example.blog_spring_mvc.service.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // for singleton purpose, there is only one Admin
@Getter
@Setter
public class Admin implements  CommandLineRunner {

    private final UserService userService;


    @Value("${info.admin.email}")
    private String adminEmail;

    @Value("${info.admin.password}")
    private String adminPassword;


    private String name = "admin";

    private String email = adminEmail;

//    @NotBlank
    private String password = adminPassword;

    private RoleType roleType = RoleType.ADMIN_ROLE;

    private boolean isEnabled=true;

    @Autowired
    public Admin(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminToFind = userService.findUserByEmail(this.email);
        if (adminToFind.isEmpty()){
            User admin = new User();
            admin.setName(this.name);
            admin.setEmail(this.email);
            admin.setPassword(this.password);
            admin.setRoleType(this.roleType);
            admin.setEnabled(this.isEnabled);
            userService.saveUser(admin);

        }

    }
}
