package com.generic.ecom.repo;

import com.generic.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer> {
    // Here i am not having findById method but still i can access thatt is the power of JPARepository
}

