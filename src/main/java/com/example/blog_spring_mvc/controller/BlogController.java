package com.example.blog_spring_mvc.controller;

import com.example.blog_spring_mvc.dto.BaseResponseDTO;
import com.example.blog_spring_mvc.dto.UserSignInDTO;
import com.example.blog_spring_mvc.entity.BlogPost;
import com.example.blog_spring_mvc.entity.Commentary;
import com.example.blog_spring_mvc.entity.User;
import com.example.blog_spring_mvc.service.BlogServiceImpl;
import com.example.blog_spring_mvc.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class BlogController {

    private final BlogServiceImpl blogService;


    @Value("my recipes blog")
    private String blogName;

    @Value("hello@myrecipes.com")
    private String contactEmail;

    @Autowired
    public BlogController(BlogServiceImpl blogService) {
        this.blogService = blogService;
    }

    // HOME
    @GetMapping("/")
    public String getHome(Model model) {
        List<BlogPost> blogPosts = blogService.getAllBlogPost();
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("blogName", blogName);
        model.addAttribute("contactEmail", contactEmail);
        return "home";
    }


    // BLOG POST

    @GetMapping("/details/{postId}")
    public String getPostDetailsAndCommentaries(@PathVariable("postId") UUID id, Model model) {
        BlogPost blogPost = blogService.getBlogPostById(id);

        BlogServiceImpl castedRepo = (BlogServiceImpl) blogService;
        List<Commentary> commentaryList = castedRepo.getAllCommentariesByPostId(id);

        if (blogPost != null) {
            model.addAttribute("blogPost", blogPost);
            model.addAttribute("commentaryList", commentaryList);
            model.addAttribute("blogName", blogName);
            model.addAttribute("contactEmail", contactEmail);
            return "post-details";
        } else {
            return "redirect:/";
        }

    }

    // BLOG POST FORM

    @GetMapping("/post-form")
    public String getThePostForm(Model model) {

        model.addAttribute("blogPost", new BlogPost());

        return "post-form";

    }

    @GetMapping("/post-form/{blogPostId}")
    public String showUpdateForm(@PathVariable("blogPostId") UUID id, Model model) {
        BlogPost blogPost = blogService.getBlogPostById(id);
        if (blogPost != null) {
            model.addAttribute("blogPost", blogPost);
            return "/post-form";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/addOrUpdateAPost")
    public String addOrUpdateAPostLogic(@Valid @ModelAttribute BlogPost blogPost, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (blogPost.getId() != null) {
                model.addAttribute("blogPost", blogPost);
                return "post-form";

            } else {
                return "post-form";

            }
        } else {
            if (blogPost.getId() != null) {
                blogService.updateBlogPost(blogPost.getId(), blogPost);
                return "redirect:/";

            } else {

                blogService.saveBlogPost(blogPost);
                return "redirect:/";
            }
        }
    }


    @GetMapping("/deletepost/{postId}")
    public String deleteAPost(@PathVariable("postId") UUID id) {
        blogService.deleteBlogPost(id);
        return "redirect:/";
    }

    // COMMENTARY

    @GetMapping("/com-form/{postId}")
    public String getCommentaryFormByPostId(@PathVariable("postId") UUID id, Model model) {
        BlogPost blogPost = blogService.getBlogPostById(id);
        if (blogPost != null) {
            model.addAttribute("blogPost", blogPost);
            model.addAttribute("commentary", new Commentary());
            return "com-form";

        } else {
            return "redirect:/details/{postId}";
        }
    }

    @PostMapping("/addCommentary/{postId}")
    public String submitACommentaryForAPost(@PathVariable("postId") UUID id, @Valid @ModelAttribute("commentary") Commentary commentary, BindingResult bindingResult, Model model) {
        BlogPost blogPost = blogService.getBlogPostById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("blogPost", blogPost);
            return "com-form";
        } else {
            commentary.setBlogPost(blogPost);
            blogService.saveCommentary(commentary);
            return "redirect:/details/" + id;
        }

    }

    @GetMapping("/deletecomment/{postId}/{commentId}")
    public String deleteComment(@PathVariable("commentId") UUID commentId, @PathVariable("postId") UUID postId) {
        blogService.deleteCommentary(commentId);
        return "redirect:/details/" + postId;

    }


}
