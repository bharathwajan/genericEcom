package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class HomepageController {

    private final static String RESPONSE_ATTR_NAME = "response";
    private final ProductsRepository repo;

    Map<String, String> response = new HashMap<>();
    public HomepageController(ProductsRepository repo){
        //constructor injection
        this.repo=repo;
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
        Integer prodId= repo.save(prods).getProdId();
        response.put(RESPONSE_ATTR_NAME, String.format("Product %d added sucessfully",prodId));
        return response;
    }

    @GetMapping("/getCSRFtoken")
    public CsrfToken getCSRF(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        System.out.println("Get request received for the  Product : " + prodId);
        Optional<Products> product=repo.findById(prodId);
        Map<String, Products> response = new HashMap<>();
        response.put(RESPONSE_ATTR_NAME, product.get());
        return response;
    }

    @PutMapping("/updateProduct")
    public Map<String,String> updateProducts(@RequestBody Products prod){
        Integer prodId=repo.save(prod).getProdId();
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d updated sucessfully",prodId));
        return  response;
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public Map<String,String> deleteProduct(@PathVariable Integer prodId){
        repo.deleteById(prodId);
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d deleted sucessfully",prodId));
        return  response;
    }
}
