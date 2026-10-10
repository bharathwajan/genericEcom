package com.generic.ecom.view.Services;

import com.generic.ecom.model.ProductOwners;
import com.generic.ecom.model.Products;
import com.generic.ecom.DAOs.ProductsRepo;
import com.generic.ecom.view.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Scope("singleton") // single ton is the default scope for service layer, but just to be explicit we are mentioning it here .
public class ProductService {
    /*
    Everthing related to product management will be here, this is the service layer which will interact with the repository layer
    and provide business logic if needed. for now it will just be a pass through to the repository.
     */
    private final ProductsRepo repository;
    private final Constants constants;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductsRepo repository, Constants constants){
        this.repository = repository;
        this.constants=constants;
    }

    @Cacheable(value=Constants.CacheConstants.product,key = "#id",sync = true)
    public Optional<Products> getProductById(Integer id){
        ProductOwners productOwner = repository.findById(id).map(Products::getOwner).orElse(null); // just to demonstrate how to get the product owner since product owner wont be fetched due to LAZY initialization in products entity
        logger.info("The owner of the product {} {}", id, productOwner==null ? null : productOwner.getFirstName());
        return repository.findById(id);
    }

    // you can  assume the cache
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

    public List<Products> saveAllProducts(List<Products> products) {
        return repository.saveAll(products); // save all is implicitly transactional
    }

    public Optional<List<Products>> getProductByNames(String prodName){
        return repository.findProductsByName(prodName);
    }
}
