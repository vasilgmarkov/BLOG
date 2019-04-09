package com.vasilgmarkov.blog.blog.controllers;

import com.vasilgmarkov.blog.blog.config.CustomUserDetails;
import com.vasilgmarkov.blog.blog.entities.Post;
import com.vasilgmarkov.blog.blog.service.FileStorageService;
import com.vasilgmarkov.blog.blog.service.PostService;
import com.vasilgmarkov.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/posts")
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @PostMapping(value = "/post")
    public String publishPost(@RequestParam("title") String title, @RequestParam("body") String body, @RequestParam("file") MultipartFile file){
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(fileName)
                .toUriString();
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setImage("/images/"+fileName);
        post.setTitle(title);
        post.setBody(body);
        post.setCreationDate(new Date());
       // post.setCreator(userService.getUser(userDetails.getUsername()));
        postService.insertPost(post);

       return "Post was published!";
    }

    @PostMapping(value = "/post/image")
    public String postImage(@RequestParam("file") MultipartFile file){

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(fileName)
                .toUriString();

        return "Post was published!  "+ fileName + " -- "+fileDownloadUri ;
    }

    @GetMapping(value = "/posts/{username}")
    public List<Post> postsByUsername(@PathVariable String username){

        return postService.findByUser(userService.getUser(username));
    }

    @DeleteMapping(value = "/post/{id}")
    public boolean deletePost(@PathVariable Long id){


         return postService.deletePost(id);
    }

    @GetMapping(value = "/postById/{id}")
    public Post getPostById(@PathVariable Long id){

        return postService.getPost(id);
    }




}
