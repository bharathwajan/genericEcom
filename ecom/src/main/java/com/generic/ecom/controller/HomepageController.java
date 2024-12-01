package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class HomepageController {

    @Autowired
    public ProductsRepository repo; // field injection

    @GetMapping("/healthCheck")
    public Map<String, String> healthCheck(){
        Map<String, String> response = new HashMap<>();
        response.put("response", "I am healthy");
        return response; // just put hashmap spring will convert to json
    }

    @PostMapping("/putProducts")
    public Map<String,String> putProducts(@RequestBody Products prods){
        Integer productId=repo.save(prods);
        Map<String, String> response = new HashMap<>();
        response.put("response", "product added sucessfully");
        response.put("productId",productId.toString());
        return response;
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        Products product=repo.findById(prodId);
        Map<String, Products> response = new HashMap<>();
        response.put("response", product);
        return response;
    }
}
