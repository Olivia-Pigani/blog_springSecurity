package com.example.blog_spring_mvc.service;

import com.example.blog_spring_mvc.model.Admin;
import com.example.blog_spring_mvc.entity.BlogPost;
import com.example.blog_spring_mvc.entity.Commentary;
import com.example.blog_spring_mvc.repository.BlogPostRepository;
import com.example.blog_spring_mvc.repository.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
public class BlogServiceImpl implements IBlogService {

    private final BlogPostRepository blogPostRepository;
    private final CommentaryRepository commentaryRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public BlogServiceImpl(BlogPostRepository blogPostRepository, CommentaryRepository commentaryRepository, ResourceLoader resourceLoader) {
        this.blogPostRepository = blogPostRepository;
        this.commentaryRepository = commentaryRepository;
        this.resourceLoader = resourceLoader;


        //Fill the database at the app start

        BlogPost applePie = BlogPost.builder()
                .id(UUID.randomUUID())
                .title("Simple apple pie recipe")
                .postContent(loadFakeContent("fakecontent.txt"))
                .postDate(Date.from(Instant.now()))
                .authorName("Phillipe Etchebest")
                .imageUrl("/image/apple-pie.jpg")
                .build();

        BlogPost mochi = BlogPost.builder()
                .id(UUID.randomUUID())
                .title("Tea mochi")
                .postContent(loadFakeContent("fakecontent.txt"))
                .postDate(Date.from(Instant.now()))
                .authorName("Kim Oh")
                .imageUrl("/image/tea-mochi.jpg")
                .build();

        BlogPost schnecKenKuchen = BlogPost.builder()
                .id(UUID.randomUUID())
                .title("Alsacian Schneckenkuchen")
                .postContent(loadFakeContent("fakecontent.txt"))
                .postDate(Date.from(Instant.now()))
                .authorName("Greta Krotz")
                .imageUrl("/image/german-cake.jpg")
                .build();


        Commentary com1ApplePie = Commentary.builder()
                .userName("andré")
                .email("andre@gmail.com")
                .content("very cool recipe !")
                .blogPost(applePie)
                .build();


        blogPostRepository.save(applePie);
        blogPostRepository.save(mochi);
        blogPostRepository.save(schnecKenKuchen);
        commentaryRepository.save(com1ApplePie);



    }

    // CRUD
    @Override
    public BlogPost saveBlogPost(BlogPost newBlogPost) {
        if (newBlogPost.getId() == null) {
            newBlogPost.setId(UUID.randomUUID());
            newBlogPost.setPostDate(Date.from(Instant.now()));
            return blogPostRepository.save(newBlogPost);
        }

        return null;
    }

    @Override
    public BlogPost getBlogPostById(UUID id) {
        return blogPostRepository.findById(id).orElse(null);
    }

    @Override
    public List<BlogPost> getAllBlogPost() {
        return blogPostRepository.findAll();
    }

    @Override
    public boolean updateBlogPost(UUID id, BlogPost blogPostToUpdate) {
        BlogPost blogPostToFind = blogPostRepository.findById(id).orElse(null);
        if (blogPostToFind != null) {
            blogPostToFind.setTitle(blogPostToUpdate.getTitle());
            blogPostToFind.setPostContent(blogPostToUpdate.getPostContent());
            blogPostToFind.setImageUrl(blogPostToUpdate.getImageUrl());
            blogPostToFind.setAuthorName(blogPostToUpdate.getAuthorName());
            blogPostToFind.setPostDate(blogPostToUpdate.getPostDate());
            blogPostRepository.save(blogPostToFind);

            return true;
        }
        return false;
    }

    @Override
    public void deleteBlogPost(UUID id) {

        blogPostRepository.deleteById(id);

    }

    @Override
    public Commentary saveCommentary(Commentary newCommentary) {
        if (newCommentary.getId() == null) {
            newCommentary.setId(UUID.randomUUID());
            return commentaryRepository.save(newCommentary);
        }

        return null;
    }

    @Override
    public Commentary getCommentaryById(UUID id) {
        return commentaryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Commentary> getAllCommentary() {
        return commentaryRepository.findAll();
    }

    @Override
    public boolean updateCommentary(UUID id, Commentary commentaryToUpdate) {
        Commentary commentaryToFind = commentaryRepository.findById(id).orElse(null);
        if (commentaryToFind != null) {
            commentaryToFind.setUserName(commentaryToUpdate.getUserName());
            commentaryToFind.setEmail(commentaryToUpdate.getEmail());
            commentaryToFind.setContent(commentaryToUpdate.getContent());
            commentaryRepository.save(commentaryToFind);
            return true;
        }

        return false;
    }

    @Override
    public void deleteCommentary(UUID id) {
        commentaryRepository.deleteById(id);
    }


    // SPECIFIC METHODS

    public String loadFakeContent(String path) {
        try {
            Resource resource = resourceLoader.getResource("classpath:/post-content/" + path);
            try (InputStream inputStream = resource.getInputStream();
                 Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error while reading the file";
    }


    public List<Commentary> getAllCommentariesByPostId(UUID postId) {

        return commentaryRepository.getCommentariesByBlogPost_Id(postId);

    }


}
