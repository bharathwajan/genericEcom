package com.generic.ecom.view.Schedulers;

import com.generic.ecom.model.Products;
import com.generic.ecom.model.Users;
import com.generic.ecom.repo.ProductsRepo;
import com.generic.ecom.repo.UserRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class NewProductNotifierViaEmail {
    private final UserRepo userRepo;
    private final ProductsRepo productsRepo;

    public NewProductNotifierViaEmail(UserRepo userrepo, ProductsRepo productsRepo){
        this.userRepo=userrepo;
        this.productsRepo=productsRepo;
    }

    @Scheduled(cron = "0 0 21 * * *") // Runs at 2:00:00 AM every day
    public void recommendationServiceToUser(){
        // This is a simple implementation of a recommendation service that notifies users about new products via email. In future this will be replaced with a more sophisticated recommendation algorithm that takes into account the user's browsing history, purchase history, and other factors to provide personalized recommendations.
        try{
            int randomNum = ThreadLocalRandom.current().nextInt(0, 99 + 1);
            Users user = userRepo.getUserByUserName("bharathwajanr");
            Products product = productsRepo.findById(randomNum).orElseThrow(()->new Exception("Product not found with id: "+randomNum));
            System.out.println("Notifying user "+user.getUserName()+" about new product: "+product.getProdName());
        }
        catch (Exception e){
            System.out.println("Error while notifying users about new product: "+e.getMessage());
        }
    }

    @Scheduled(fixedRate = 1000*10)
    public void flushTheCache(){
        try{
            System.out.println("Flush the data cache every 5 seconds (Scheduler Example)");
        }
        catch (Exception e){
            System.out.println("Error while notifying users about new product: "+e.getMessage());
        }
    }
}
