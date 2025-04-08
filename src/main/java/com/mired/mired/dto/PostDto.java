package com.mired.mired.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDto {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    private String imageUrl;
}
