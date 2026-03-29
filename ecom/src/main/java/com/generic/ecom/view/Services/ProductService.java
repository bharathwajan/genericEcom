package com.generic.ecom.view.Services;

import com.generic.ecom.model.Products;
import com.generic.ecom.repo.ProductsRepo;
import com.generic.ecom.view.Constants;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    private final Constants constants;

    public ProductService(ProductsRepo repository, Constants constants){
        this.repository = repository;
        this.constants=constants;
    }

    @Cacheable(value=Constants.CacheConstants.product,key = "#id")
    public Optional<Products> getProductById(Integer id){
        return repository.findById(id);
    }

    // you Can  assume the cache
    // like a hashmap where the variable name is Constants.CacheConstants.product and
    // actual key is p.prodId which is what is what gets updated
    @CachePut(value=Constants.CacheConstants.product,key = "#p.prodId")
    public Products saveProduct(Products p){
        return repository.save(p);
    }

    @CacheEvict(value = Constants.CacheConstants.product,key = "#id")
    public void deleteProductById(Integer id){
        repository.deleteById(id);
    }

    public List<Products> getAllProducts(){
        return repository.findAll();
    }

}
