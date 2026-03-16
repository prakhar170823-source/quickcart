package com.quickcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickcart.dto.ProductDTOs.*;
import com.quickcart.security.JwtService;
import com.quickcart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void createProduct_WithValidInput_ShouldReturn201() throws Exception {

        ProductReq req = new ProductReq("Keyboard", "Electronics", new BigDecimal("50.00"));
        ProductRes res = new ProductRes(1L, "Keyboard", "Electronics", new BigDecimal("50.00"));

        when(productService.createProduct(any(ProductReq.class))).thenReturn(res);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(res.id()))
                .andExpect(jsonPath("$.name").value(res.name()));
    }

    @Test
    void createProduct_WithInvalidInput_ShouldReturn400() throws Exception {

        ProductReq invalidReq = new ProductReq("", "Electronics", new BigDecimal("50.00"));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReq)))

                .andExpect(status().isBadRequest());
    }
}
