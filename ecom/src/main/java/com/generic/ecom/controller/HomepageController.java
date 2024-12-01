package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepository;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class HomepageController {

    private final static String responseAttrName = "response";
    private final ProductsRepository repo;


    Map<String, String> response = new HashMap<>();
    public HomepageController(ProductsRepository repo){
        //constructor injection
        this.repo=repo;
    }


    @GetMapping("/healthCheck")
    public Map<String, String> healthCheck(){
        response.put(responseAttrName, "I am healthy");
        return response; // just put hashmap spring will convert to json
    }

    @PostMapping("/putProducts")
    public Map<String,String> putProducts(@RequestBody Products prods){
        Integer prodId=repo.save(prods);
        response.put(responseAttrName, String.format("Product %d added sucessfully",prodId));
        return response;
    }

    @GetMapping("/getProduct/{prodId}")
    public Map<String,Products> getProduct(@PathVariable Integer prodId){
        Products product=repo.findById(prodId);
        Map<String, Products> response = new HashMap<>();
        response.put(responseAttrName, product);
        return response;
    }

    @PutMapping("/updateProduct")
    public Map<String,String> updateProducts(@RequestBody Products prod){
        Integer prodId=repo.update(prod);
        response.put(responseAttrName,String.format("Product %d updated sucessfully",prodId));
        return  response;
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public Map<String,String> deleteProduct(@PathVariable Integer prodId){
        repo.delete(prodId);
        response.put(responseAttrName,String.format("Product %d deleted sucessfully",prodId));
        return  response;
    }
}
