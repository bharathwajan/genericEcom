package com.generic.ecom.view.Services;

import com.generic.ecom.model.Products;
import com.generic.ecom.DAOs.ProductsRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductsRepo productsRepo; // this will be injected into the productService
    @InjectMocks // this annotation will get the mock object and inject it into the productService
    private ProductService productService;

    @BeforeAll
    static void beforeAll(){
        // Yes, by default JUnit 5 creates a new instance of the test class for every test method. @BeforeAll is not used to avoid that.
    }

    @BeforeEach
    void beforeEach(){
        // This method will be executed before each test method. You can use it to set up common test data or configurations.
    }

    @Test
    void getProductById() {
        Products sampleProduct =new Products();
        sampleProduct.setProdId(2);
        Mockito.when(productService.getProductById(2)).thenReturn(Optional.of(sampleProduct));
        Optional<Products> returnedProduct  =productService.getProductById(2);
        assertTrue(returnedProduct.map(Products::getProdId).orElse(3).equals(2));
    }

    @Test
    void saveProduct() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void getAllProducts() {
    }
}