package com.generic.ecom.DAOs;

import com.generic.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer> {
    // Here i am not having findById method but still i can access thatt is the power of JPARepository
    @Query("SELECT p FROM Products p WHERE p.prodName = :prodName")
    Optional<List<Products>> findProductsByName(@Param("prodName") String prodName);
}

