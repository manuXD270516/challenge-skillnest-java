package com.mired.mired.controller;

import com.mired.mired.dto.ApiResponse;
import com.mired.mired.dto.PostDto;
import com.mired.mired.dto.PostRequest;
import com.mired.mired.dto.PostResponse;
import com.mired.mired.model.Post;
import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.security.RoleRequired;
import com.mired.mired.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponse> response = posts.stream()
                .map(p -> PostResponse.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .category(p.getCategory())
                        .description(p.getDescription())
                        .imageUrl(p.getImageUrl())
                        .userEmail(p.getUserEmail()) // 👈 Agregado aquí
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.status(404).body(ApiResponse.builder().message("Publicación no encontrada").build());
        }
        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .description(post.getDescription())
                .imageUrl(post.getImageUrl())
                .userEmail(post.getUserEmail()) // 👈 También aquí
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequest request, Authentication authentication) {
        PostDto dto = PostDto.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
        postService.createPost(dto, authentication.getName());
        return ResponseEntity.ok(ApiResponse.builder().message("Publicación creada exitosamente.").build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody @Valid PostRequest request) {
        PostDto dto = PostDto.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
        Post updated = postService.updatePost(id, dto);
        if (updated == null) {
            return ResponseEntity.status(404).body(ApiResponse.builder().message("Publicación no encontrada").build());
        }
        return ResponseEntity.ok(ApiResponse.builder().message("Publicación actualizada.").build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.builder().message("Publicación eliminada.").build());
    }
}