package com.quickcart.service;

import com.quickcart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.quickcart.dto.ProductDTOs.*;
import com.quickcart.model.Product;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductRes createProduct(ProductReq req) {
        Product p = new Product();
        p.setName(req.name());
        p.setCategory(req.category());
        p.setPrice(req.price());
        return mapToRes(productRepository.save(p));
    }

    public List<ProductRes> getProducts(String category, BigDecimal min, BigDecimal max) {

        List<Product> products = switch (category) {
            case "" -> productRepository.findByPriceBetween(min, max);
            case null -> productRepository.findByPriceBetween(min, max);
            default -> productRepository.findByCategoryAndPriceBetween(category, min, max);
        };

        return products.stream()
                .map(this::mapToRes)
                .toList();
    }

    public ProductRes updateProduct(Long id, ProductReq req) {

        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        p.setName(req.name());
        p.setCategory(req.category());
        p.setPrice(req.price());

        return mapToRes(productRepository.save(p));
    }

    public void softDelete(Long id) {
        productRepository.findById(id).ifPresent(p -> {
            p.setDeleted(true);
            productRepository.save(p);
        });
    }

    private ProductRes mapToRes(Product p) {
        return new ProductRes(p.getId(), p.getName(), p.getCategory(), p.getPrice());
    }
}
