package com.example.blog_spring_mvc.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "you must type your username")
    @NotBlank
    @Size(min = 2,max = 50)
    private String userName;


    @NotNull
    @NotBlank
    @Email(message = "you must type a valid email")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 2,max = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

}
