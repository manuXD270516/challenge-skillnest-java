package com.mired.mired.repository;

import com.mired.mired.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserEmail(String email);
}
