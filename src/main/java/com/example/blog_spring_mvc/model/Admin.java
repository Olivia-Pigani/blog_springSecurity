package com.example.blog_spring_mvc.model;


import lombok.*;

@Getter
@Setter
public class Admin {


    private String adminMail = "admin@email.com";

    private String password ="1234aA";

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
