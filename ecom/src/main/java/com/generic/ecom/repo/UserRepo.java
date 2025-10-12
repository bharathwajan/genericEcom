package com.generic.ecom.repo;

import com.generic.ecom.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {
    // Add custom query methods if needed
    Users getUserByUserName(String userName);

    @Query("SELECT u FROM Users u WHERE u.userName = :username")
    Optional<Users> findByUserName(@Param("username") String username);

    // 2. Find by email using native SQL
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<Users> findByEmail(@Param("email") String email);

    // 3. Find all users by last name (JPQL)
    @Query("SELECT u FROM Users u WHERE u.lastName = :lastName")
    List<Users> findByLastName(@Param("lastName") String lastName);
}
