package com.example.blog_spring_mvc.repository;

import com.example.blog_spring_mvc.entity.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentaryRepository extends JpaRepository<Commentary, UUID> {

    public List<Commentary> getCommentariesByBlogPost_Id (UUID id);



}
