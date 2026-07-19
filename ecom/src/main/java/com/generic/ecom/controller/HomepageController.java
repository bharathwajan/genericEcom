package com.generic.ecom.controller;
import com.example.Xlibs.Services.GreetingService;
import com.generic.ecom.DTOs.HomePageControllerResponse;
import com.generic.ecom.model.Products;
import com.generic.ecom.view.Services.NotificationServiceGRPCClient;
import com.generic.ecom.view.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
public class HomepageController {

    private final Logger logger = LoggerFactory.getLogger(HomepageController.class);
    private final static String RESPONSE_ATTR_NAME = "response";
    private final ProductService productService;
    private final NotificationServiceGRPCClient notificationService;
    private final com.example.Xlibs.Services.GreetingService greetingService;


    public HomepageController(ProductService productService, NotificationServiceGRPCClient notificationService, GreetingService greetingService) {
        this.notificationService = notificationService;
        this.productService = productService;
        this.greetingService = greetingService;
    }

    @GetMapping("/getProduct/{prodId}")
    public ResponseEntity<HomePageControllerResponse<Products>> getProduct(@PathVariable Integer prodId){
        logger.info("Get request received for the  Product : {}", prodId);
        Optional<Products> product = productService.getProductById(prodId);
        notificationService.sendNotification( "bharathwajanr", "notification from ecom service");
        HomePageControllerResponse<Products> response = new HomePageControllerResponse<>(
                true,
                String.format("Product %d fetched successfully", prodId),
                product.orElse(null));
        logger.info("Get request completed for the  Product : {}", prodId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/getProductByName/{prodName}")
    public ResponseEntity<HomePageControllerResponse<List<Products>>> getProductByName(@PathVariable String prodName){
        logger.info("Get request received for the  Product : {}", prodName);
        Optional<List<Products>> product = productService.getProductByNames(prodName);

        HomePageControllerResponse<List<Products>> response = new HomePageControllerResponse<>(
                true,
                product.isPresent() ? "Sucess" : "Failed",
                product.orElse(null));

        logger.info("Get request completed for the  Product : {}", prodName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<HomePageControllerResponse<String>> homepage(HttpServletRequest request) {
        String message = "Welcome Bharathwajan " + request.getSession().getId();
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(
                true,
                "Homepage loaded successfully",
                message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<HomePageControllerResponse<String>> healthCheck() {

        HomePageControllerResponse<String> apiResponse = new HomePageControllerResponse<>(
                true,
                "Health check successful",
                null);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/putProducts")
    public ResponseEntity<HomePageControllerResponse<String>> putProducts(@RequestBody Products prods) {
        Integer prodId = productService.saveProduct(prods).getProdId();
        String message = String.format("Product %d added successfully", prodId);
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(
                true,
                "Product added successfully",
                message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCSRFtoken")
    public ResponseEntity<HomePageControllerResponse<CsrfToken>> getCSRF(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        HomePageControllerResponse<CsrfToken> response = new HomePageControllerResponse<>(
                true,
                "CSRF token fetched successfully",
                csrfToken);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<HomePageControllerResponse<Integer>> updateProducts(@RequestBody Products prod) {
        Integer prodId = productService.saveProduct(prod).getProdId();
        HomePageControllerResponse<Integer> response = new HomePageControllerResponse<>(
                true,
                "Product updated successfully",
                prodId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public ResponseEntity<HomePageControllerResponse<String>> deleteProduct(@PathVariable Integer prodId) {
        productService.deleteProductById(prodId);
        String message = String.format("Product %d deleted successfully", prodId);
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(
                true,
                "Product deleted successfully",
                message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/callXlibs/{name}")
    public ResponseEntity<HomePageControllerResponse<String>> callXlibs(@PathVariable String name) {
        String greeting = greetingService.sayHello(name);
        HomePageControllerResponse<String> response = new HomePageControllerResponse<>(
                true,
                "Greeting fetched successfully",
                greeting);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-all-products")
    public ResponseEntity<HomePageControllerResponse<List<Products>>> saveAllProducts(@RequestBody List<Products> products) {
        List<Products> savedProducts = productService.saveAllProducts(products);
        HomePageControllerResponse<List<Products>> response = new HomePageControllerResponse<>(
                true,
                "Products saved successfully",
                savedProducts);
        return ResponseEntity.ok(response);
    }
}
