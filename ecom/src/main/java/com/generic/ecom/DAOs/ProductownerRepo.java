package com.generic.ecom.DAOs;

import com.generic.ecom.model.ProductOwners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductownerRepo extends JpaRepository<ProductOwners, String> {
    // the second parameter is the type of the primary key of the entity class
    ProductOwners getUserByUserName(String userName);
}


