package com.quickcart.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
public class ProductDTOs {
    public record ProductReq(
            @NotBlank(message = "Product name cannot be empty")
            String name,
            @NotBlank(message = "Category cannot be empty")
            String category,

            @Positive(message = "Price must be greater than zero")
            BigDecimal price
    ) {}

    public record ProductRes(
            Long id,
            String name,
            String category,
            BigDecimal price
    ) {}
}
