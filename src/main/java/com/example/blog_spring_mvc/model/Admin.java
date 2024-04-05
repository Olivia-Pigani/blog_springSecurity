package com.example.blog_spring_mvc.model;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class Admin {

    @Value("${info.admin.email}")
    private String adminEmail;

    @Value("${info.admin.password}")
    private String adminPassword;


    private String adminMail = adminEmail;

    private String password =adminPassword;


    private static volatile Admin admin = null;

    private Admin() {
    }


    public static Admin getAdmin(){
        if (admin == null){
            synchronized (Admin.class){
                if (admin == null){
                    admin = new Admin();
                }
            }
        }
        return admin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminMail='" + adminMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
