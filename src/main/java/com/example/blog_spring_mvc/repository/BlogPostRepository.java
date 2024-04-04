package com.example.blog_spring_mvc.repository;

import com.example.blog_spring_mvc.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
}
