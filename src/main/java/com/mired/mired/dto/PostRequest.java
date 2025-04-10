package com.mired.mired.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "El título no puede estar vacío.")
    private String title;

    @NotBlank(message = "La categoría no puede estar vacía.")
    private String category;

    @NotBlank(message = "La descripción no puede estar vacía.")
    private String description;

    @NotBlank(message = "La URL de la imagen es requerida.")
    private String imageUrl;
}