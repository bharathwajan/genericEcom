package com.generic.ecom.controller;



import com.example.Xlibs.Services.GreetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.ecom.model.Products;
import com.generic.ecom.view.Services.NotificationServiceGRPCClient;
import com.generic.ecom.view.Services.ProductService;
import com.generic.ecom.view.security.JWTService;
import com.generic.ecom.view.security.ecomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomepageController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses the 403 Forbidden Spring Security issue
class HomepageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Add these two to satisfy the Spring Security filter chain dependencies:
    @MockBean
    private JWTService jwtService;

    @MockBean
    private ecomUserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper; // Used to easily convert Java objects to JSON strings

    @MockBean
    private ProductService productService;

    @MockBean
    private NotificationServiceGRPCClient notificationService;

    @MockBean
    private GreetingService greetingService;

    @Test
    void getProduct_Success() throws Exception {
        Products mockProduct = new Products();
        // Assuming Products has standard setters; adjust if yours differ
        mockProduct.setProdId(101);

        when(productService.getProductById(101)).thenReturn(Optional.of(mockProduct));
        // notificationService returns void, so Mockito does nothing by default. No setup needed.

        mockMvc.perform(get("/getProduct/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Product 101 fetched successfully"))
                .andExpect(jsonPath("$.data.prodId").value(101));
    }

    @Test
    void getProductByName_Success() throws Exception {
        Products mockProduct = new Products();
        mockProduct.setProdId(101);

        when(productService.getProductByNames("Laptop")).thenReturn(Optional.of(List.of(mockProduct)));

        mockMvc.perform(get("/getProductByName/Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucess"));
    }

    @Test
    void homepage_Success() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Homepage loaded successfully"));
    }

    @Test
    void healthCheck_Success() throws Exception {
        mockMvc.perform(get("/healthCheck"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Health check successful"));
    }

    @Test
    void putProducts_Success() throws Exception {
        Products inputProduct = new Products();

        // We MUST return a mocked product with an ID, otherwise .getProdId() in your controller will throw a NullPointerException
        Products savedProduct = new Products();
        savedProduct.setProdId(500);

        when(productService.saveProduct(any(Products.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/putProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Product added successfully"))
                .andExpect(jsonPath("$.data").value("Product 500 added successfully"));
    }

    @Test
    void getCSRFtoken_Success() throws Exception {
        // Because we disabled filters, the CsrfFilter won't run to attach this attribute naturally.
        // We must manually inject it into the mock request so your controller can retrieve it.
        DefaultCsrfToken mockToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "fake-token-123");

        mockMvc.perform(get("/getCSRFtoken")
                        .requestAttr("_csrf", mockToken)) // Injecting the attribute here
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("fake-token-123"));
    }

    @Test
    void updateProducts_Success() throws Exception {
        Products inputProduct = new Products();

        Products updatedProduct = new Products();
        updatedProduct.setProdId(999);

        when(productService.saveProduct(any(Products.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/updateProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(999));
    }

    @Test
    void deleteProduct_Success() throws Exception {
        // Mocking a void method. Not strictly necessary as Mockito does this by default,
        // but good practice for clarity.
        doNothing().when(productService).deleteProductById(101);

        mockMvc.perform(delete("/deleteProduct/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Product deleted successfully"))
                .andExpect(jsonPath("$.data").value("Product 101 deleted successfully"));
    }

    @Test
    void callXlibs_Success() throws Exception {
        when(greetingService.sayHello(anyString())).thenReturn("Hello John");

        mockMvc.perform(get("/callXlibs/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Hello John"));
    }

    @Test
    void saveAllProducts_Success() throws Exception {
        List<Products> inputList = List.of(new Products(), new Products());

        Products saved1 = new Products(); saved1.setProdId(1);
        Products saved2 = new Products(); saved2.setProdId(2);
        List<Products> savedList = List.of(saved1, saved2);

        // any() is used here to match any List
        when(productService.saveAllProducts(any())).thenReturn(savedList);

        mockMvc.perform(post("/save-all-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Products saved successfully"));
    }
}