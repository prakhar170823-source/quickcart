package com.quickcart.service;

import com.quickcart.dto.ProductDTOs.*;
import com.quickcart.model.Product;
import com.quickcart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_ShouldReturnSavedProduct() {
        ProductReq req = new ProductReq("Laptop", "Electronics", new BigDecimal("1200.00"));

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setCategory("Electronics");
        savedProduct.setPrice(new BigDecimal("1200.00"));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductRes result = productService.createProduct(req);

        assertNotNull(result);
        assertEquals(savedProduct.getName(), result.name());
        assertEquals(savedProduct.getId(), result.id());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProducts_WithoutCategory_ShouldReturnAllInPriceRange() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Mouse");
        p.setCategory("Electronics");
        p.setPrice(new BigDecimal("25.00"));

        when(productRepository.findByPriceBetween(any(), any())).thenReturn(List.of(p));

        List<ProductRes> result = productService.getProducts(null, new BigDecimal("0"), new BigDecimal("100"));

        assertEquals(1, result.size());
        assertEquals(p.getName(), result.get(0).name());
        verify(productRepository, times(1)).findByPriceBetween(any(), any());
    }
}
