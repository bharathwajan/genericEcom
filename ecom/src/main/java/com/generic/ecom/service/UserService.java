package com.generic.ecom.service;
import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // 1. Create a new user
    @Transactional
    public Users createUser(Users user) {
        // You can add validation or business logic here before saving
        return userRepo.save(user);
    }

    // 2. Retrieve a user by their username (ID)
    public Optional<Users> getUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    // 3. Retrieve all users
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    // 4. Update an existing user
    @Transactional
    public Users updateUser(Users user) {
        // Find existing user to ensure it exists before updating
        Optional<Users> existingUser = userRepo.findByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            // Business logic to update specific fields
            Users updatedUser = existingUser.get();
            updatedUser.setPassword(user.getPassword());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            return userRepo.save(updatedUser);
        } else {
            // Handle the case where the user does not exist
            throw new IllegalArgumentException("User with username " + user.getUserName() + " not found.");
        }
    }

    // 5. Delete a user by their username
    @Transactional
    public void deleteUserByUserName(String userName) {
        userRepo.deleteById(userName);
    }
}
