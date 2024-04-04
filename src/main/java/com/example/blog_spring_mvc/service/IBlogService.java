package com.example.blog_spring_mvc.service;

import com.example.blog_spring_mvc.entity.BlogPost;
import com.example.blog_spring_mvc.entity.Commentary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBlogService {


//CRUD

    // BlogPost

    public BlogPost saveBlogPost(BlogPost newBlogPost);

    public BlogPost getBlogPostById(UUID id);

    public List<BlogPost> getAllBlogPost();

    public boolean updateBlogPost(UUID id, BlogPost blogPostToUpdate);

    public void deleteBlogPost(UUID id);



    // Commentary

    public Commentary saveCommentary(Commentary newCommentary);

    public Commentary getCommentaryById(UUID id);

    public List<Commentary> getAllCommentary();

    public boolean updateCommentary(UUID id, Commentary commentaryToUpdate);

    public void deleteCommentary(UUID id);



}
