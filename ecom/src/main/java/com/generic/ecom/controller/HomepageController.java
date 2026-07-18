package com.generic.ecom.controller;

import com.example.Xlibs.Services.GreetingService;
import com.generic.ecom.model.Products;
import com.generic.ecom.view.Services.NotificationServiceGRPCClient;
import com.generic.ecom.view.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class HomepageController {

    private final Logger logger = LoggerFactory.getLogger(HomepageController.class);
    private final static String RESPONSE_ATTR_NAME = "response";
    private final ProductService productService;
    private final NotificationServiceGRPCClient notificationService;
    private final com.example.Xlibs.Services.GreetingService greetingService;

    Map<String, String> response = new HashMap<>();
    public HomepageController(ProductService productService, NotificationServiceGRPCClient notificationService, GreetingService greetingService) {
        this.notificationService = notificationService;
        this.productService = productService;
        this.greetingService = greetingService;
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        logger.info("Get request received for the  Product : {}", prodId);
        Optional<Products> product = productService.getProductById(prodId);
        Map<String, Products> response = new HashMap<>();
        notificationService.sendNotification( "bharathwajanr", "notification from ecom service");
        response.put(RESPONSE_ATTR_NAME, product.orElse(null));
        logger.info("Get request completed for the  Product : {}", prodId);
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

    @GetMapping("/callXlibs/{name}")
    public Map<String,String> callXlibs(@PathVariable String name){
        response.put(RESPONSE_ATTR_NAME,greetingService.sayHello(name));
        return  response;
    }

    @PostMapping("/save-all-products")
    public ResponseEntity<?> saveAllProducts(@RequestBody List<Products> products) {
        List<Products> savedProducts = productService.saveAllProducts(products);
        return ResponseEntity.ok(savedProducts);
    }
}
