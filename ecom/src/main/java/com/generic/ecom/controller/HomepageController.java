package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class HomepageController {
    private final static String RESPONSE_ATTR_NAME = "response";
    private final ProductsRepository repo;
    private Map<String, String> response = new HashMap<>(); // doing like this is not thread safe
    //The response map will only be garbage collected if the entire HomepageController bean itself is:
    // Removed from the Spring context Dereferenced by all active threads

    private Map<String, String> HitCounter = new HashMap<>(); // doing like this is not thread safe
    @GetMapping("/")
    public String homepage(HttpServletRequest request){
        for(int i=0;i<10000;i++){
            HitCounter.put("Count"+String.valueOf(i), String.valueOf(i));
        }
        return "welcome Bharathwajan "+request.getSession().getId();
    }

    public HomepageController(ProductsRepository repo){
        //constructor injection
        this.repo=repo;
    }



    @GetMapping("/healthCheck")
    public Map<String, String> healthCheck(){
        response.put(RESPONSE_ATTR_NAME, "I am healthy");
        return response; // just put hashmap spring will convert to json
    }

    @PostMapping("/putProducts")
    public Map<String,String> putProducts(@RequestBody Products prods){
        Integer prodId=repo.save(prods);
        response.put(RESPONSE_ATTR_NAME, String.format("Product %d added sucessfully",prodId));
        return response;
    }

    @GetMapping("/getCSRFtoken")
    public CsrfToken getCSRF(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        Products product=repo.findById(prodId);
        Map<String, Products> response = new HashMap<>();
        response.put(RESPONSE_ATTR_NAME, product);
        return response;
    }

    @PutMapping("/updateProduct")
    public Map<String,String> updateProducts(@RequestBody Products prod){
        Integer prodId=repo.update(prod);
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d updated sucessfully",prodId));
        return  response;
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public Map<String,String> deleteProduct(@PathVariable Integer prodId){
        repo.delete(prodId);
        response.put(RESPONSE_ATTR_NAME,String.format("Product %d deleted sucessfully",prodId));
        return  response;
    }
}
