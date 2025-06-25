package com.generic.ecom.repo;

import com.generic.ecom.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {
    // Add custom query methods if needed
    Users getUserByUserName(String userName);
}
