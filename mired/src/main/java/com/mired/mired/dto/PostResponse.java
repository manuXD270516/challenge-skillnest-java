package com.mired.mired.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String imageUrl;
    private String userEmail;
}