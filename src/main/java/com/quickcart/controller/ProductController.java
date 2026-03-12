package com.quickcart.controller;

import com.quickcart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.quickcart.dto.ProductDTOs.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Catalog", description = "CRUD operations for products")
@SecurityRequirement(name = "BearerAuth")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    public ProductRes create(@Valid @RequestBody ProductReq req) {
        return productService.createProduct(req);
    }

    @GetMapping
    @Operation(summary = "List and filter products", description = "Filter products by category and price range.")
    public List<ProductRes> list(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") BigDecimal minPrice,
            @RequestParam(defaultValue = "1000000") BigDecimal maxPrice) {

        return productService.getProducts(category, minPrice, maxPrice);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ProductRes update(
            @PathVariable Long id,
            @Valid @RequestBody ProductReq req) {

        return productService.updateProduct(id, req);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    @Operation(summary = "Soft delete a product", description = "Flags a product as deleted without removing it from the database.")
    public void delete(@PathVariable Long id) {
        productService.softDelete(id);
    }
}
