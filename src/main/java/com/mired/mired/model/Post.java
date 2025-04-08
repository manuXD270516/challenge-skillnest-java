package com.mired.mired.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private String imageUrl;
    private String userEmail;
}
