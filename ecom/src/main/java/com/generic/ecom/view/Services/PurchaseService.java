package com.generic.ecom.view.Services;

import com.generic.ecom.model.ProductOwners;
import com.generic.ecom.model.Products;
import com.generic.ecom.model.Users;
import com.generic.ecom.DAOs.ProductownerRepo;
import com.generic.ecom.DAOs.ProductsRepo;
import com.generic.ecom.DAOs.UserRepo;
import com.generic.ecom.view.ExceptionHandling.BusinessException;
import com.generic.ecom.view.ExceptionHandling.ErrorCode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final UserRepo userRepo;
    private final ProductsRepo productsRepo;
    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    public PurchaseService(ProductownerRepo productOwnerRepo, UserRepo userRepo, ProductsRepo productsRepo) {

        this.userRepo = userRepo;
        this.productsRepo = productsRepo;
    }

    @Transactional
    public boolean purchaseProduct(Integer productId) {
        // we have to get the user from JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Users user=userRepo.getUserByUserName(userDetails.getUsername()); // user who is purchasing the product
        Products product=productsRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductOwners productOwners=product.getOwner(); // owner of the product who is selling the product

        if (user.getWalletBalance().compareTo(product.getProductPrice()) < 0) {
            logger.info("Insufficient balance for user {} to purchase product {}", user.getUserName(), product.getProdName());
            throw new BusinessException(400, ErrorCode.INSUFFICIENT_BALANCE,"Insufficient balance for the user %s for buying the product %s from %s".formatted(user.getUserName(),product.getProdName(),product.getOwner().getFirstName()));
        }
        user.setWalletBalance(user.getWalletBalance().subtract(product.getProductPrice()));
        productOwners.setEarnings(productOwners.getEarnings().add(product.getProductPrice()));
        return true; // This ideally constructs SQL Transaction with BEGIN and COMMIT based on the above operation
    }
}
