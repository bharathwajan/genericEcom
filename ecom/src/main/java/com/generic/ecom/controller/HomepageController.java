package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import com.generic.ecom.view.Services.NotificationServiceGRPCClient;
import com.generic.ecom.view.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class HomepageController {

    private final static String RESPONSE_ATTR_NAME = "response";
    private final ProductService productService;
    private final NotificationServiceGRPCClient notificationService;

    Map<String, String> response = new HashMap<>();
    public HomepageController(ProductService productService, NotificationServiceGRPCClient notificationService) {
        this.notificationService = notificationService;
        this.productService = productService;
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        System.out.println("Get request received for the  Product : " + prodId);
        Optional<Products> product = productService.getProductById(prodId);
        Map<String, Products> response = new HashMap<>();
        notificationService.sendNotification( "bharathwajanr", "notification from ecom service");
        response.put(RESPONSE_ATTR_NAME, product.orElse(null));
        return response;
    }

    @GetMapping("/")
    public String homepage(HttpServletRequest request){
        return "welcome Bharathwajan "+request.getSession().getId();
    }

    @GetMapping("/healthCheck")
    public Map<String, String> healthCheck(){
        response.put(RESPONSE_ATTR_NAME, "I am healthy");
        return response; // just put hashmap spring will convert to json
    }

    @PostMapping("/putProducts")
    public Map<String,String> putProducts(@RequestBody Products prods){
        Integer prodId = productService.saveProduct(prods).getProdId();
        response.put(RESPONSE_ATTR_NAME, String.format("Product %d added sucessfully",prodId));
        return response;
    }

    @GetMapping("/getCSRFtoken")
    public CsrfToken getCSRF(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }

    @PutMapping("/updateProduct")
    public Map<String,String> updateProducts(@RequestBody Products prod){
        Integer prodId = productService.saveProduct(prod).getProdId();
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d updated sucessfully",prodId));
        return  response;
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public Map<String,String> deleteProduct(@PathVariable Integer prodId){
        productService.deleteProductById(prodId);
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d deleted sucessfully",prodId));
        return  response;
    }
}
