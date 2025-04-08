package com.mired.mired.controller;

import com.mired.mired.dto.PostDto;
import com.mired.mired.model.Post;
import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.security.RoleRequired;
import com.mired.mired.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@Tag(name = "Publicaciones", description = "CRUD de publicaciones")
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Crear nueva publicación (solo ADMIN)")
    @PostMapping
    @RoleRequired({Role.ADMIN})
    public ResponseEntity<?> create(@Valid @RequestBody PostDto dto, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return ResponseEntity.ok(postService.createPost(dto, user.getEmail()));
    }

    @Operation(summary = "Obtener todas las publicaciones")
    @GetMapping
    @RoleRequired({Role.ADMIN, Role.USER})
    public ResponseEntity<List<Post>> getAll(HttpSession session) {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "Ver detalle de publicación por ID")
    @GetMapping("/{id}")
    @RoleRequired({Role.ADMIN, Role.USER})
    public ResponseEntity<Post> getById(@PathVariable Long id, HttpSession session) {
        Post post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Editar publicación existente (solo ADMIN)")
    @PutMapping("/{id}")
    @RoleRequired({Role.ADMIN})
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PostDto dto, HttpSession session) {
        Post updated = postService.updatePost(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar publicación (solo ADMIN)")
    @DeleteMapping("/{id}")
    @RoleRequired({Role.ADMIN})
    public ResponseEntity<String> delete(@PathVariable Long id, HttpSession session) {
        postService.deletePost(id);
        return ResponseEntity.ok("Publicación eliminada");
    }
}