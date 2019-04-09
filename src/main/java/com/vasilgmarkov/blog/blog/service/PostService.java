package com.vasilgmarkov.blog.blog.service;

import com.vasilgmarkov.blog.blog.entities.Post;
import com.vasilgmarkov.blog.blog.entities.User;
import com.vasilgmarkov.blog.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){

        return postRepository.findAll();
    }

    public void insertPost(Post post){
        postRepository.save(post);
    }

    public List<Post> findByUser(User user) {

        return postRepository.findByCreatorId(user.getId());

    }

    public boolean deletePost(Long id) {

         Post post = postRepository.getOne(id);
         if (post == null)
             return false;
         postRepository.deleteById(id);
            return true;

    }

    public Post getPost(Long id) {
        return postRepository.getOne(id);

    }
}
