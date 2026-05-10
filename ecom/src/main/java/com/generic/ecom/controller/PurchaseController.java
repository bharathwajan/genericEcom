package com.generic.ecom.controller;

import com.generic.ecom.DTOs.PurchaseProductDTO;
import com.generic.ecom.model.Products;
import com.generic.ecom.view.Services.PurchaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchaseProduct")
    public Map<String, String> purchaseProduct(@RequestBody PurchaseProductDTO purchaseProductDTO) {
        // Logic to process the purchase
        Map<String, String> response = new HashMap<>();
        if (purchaseService.purchaseProduct(purchaseProductDTO.productId())) {
            response.put("message", "Purchase successful");
            return response;
        }
        response.put("message", "Purchase failed");
        return response;
    }
}
