package com.mired.mired.service;

import com.mired.mired.dto.PostDto;
import com.mired.mired.model.Post;
import com.mired.mired.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostDto dto, String userEmail) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setCategory(dto.getCategory());
        post.setImageUrl(dto.getImageUrl());
        post.setUserEmail(userEmail);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post updatePost(Long id, PostDto dto) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(dto.getTitle());
            post.setDescription(dto.getDescription());
            post.setCategory(dto.getCategory());
            post.setImageUrl(dto.getImageUrl());
            return postRepository.save(post);
        }).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
