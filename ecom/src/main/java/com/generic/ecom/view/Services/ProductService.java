package com.generic.ecom.view.Services;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    /*
    Everthing related to product management will be here, this is the service layer which will interact with the repository layer
    and provide business logic if needed. for now it will just be a pass through to the repository.
     */
    private final ProductsRepo repository;

    public ProductService(ProductsRepo repository){
        this.repository = repository;
    }

    @Cacheable("GetProductById")
    public Optional<Products> getProductById(Integer id){
        return repository.findById(id);
    }

    public Products saveProduct(Products p){
        return repository.save(p);
    }

    public void deleteProductById(Integer id){
        repository.deleteById(id);
    }

    public List<Products> getAllProducts(){
        return repository.findAll();
    }

}
