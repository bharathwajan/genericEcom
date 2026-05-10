package com.generic.ecom.view.Services;

import com.generic.ecom.model.ProductOwners;
import com.generic.ecom.model.Products;
import com.generic.ecom.model.Users;
import com.generic.ecom.repo.ProductownerRepo;
import com.generic.ecom.repo.ProductsRepo;
import com.generic.ecom.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {

    private final UserRepo userRepo;
    private final ProductsRepo productsRepo;

    public PurchaseService(ProductownerRepo productOwnerRepo, UserRepo userRepo, ProductsRepo productsRepo) {

        this.userRepo = userRepo;
        this.productsRepo = productsRepo;
    }

    @Transactional
    public boolean purchaseProduct(Integer productId) {
        // we have to get the user from JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Users user=userRepo.getUserByUserName(userDetails.getUsername());
        Products product=productsRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductOwners productOwners=product.getOwner();

        if (user.getWalletBalance().compareTo(product.getProductPrice()) < 0) {
            throw new RuntimeException("Insufficient balance for the user for buying the product");
        }

        user.setWalletBalance(user.getWalletBalance().subtract(product.getProductPrice()));
        productOwners.setEarnings(productOwners.getEarnings().add(product.getProductPrice()));
        return true;
    }
}
