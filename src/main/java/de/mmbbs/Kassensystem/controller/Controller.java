package de.mmbbs.Kassensystem.controller;

import de.mmbbs.Kassensystem.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private final DatabaseService databaseService;

    public Controller(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Get Mapping for retrieving Product Information
    @GetMapping("/get-product-data-by-name")
    public void getProductInformationByName(@RequestParam String product_name) {
        databaseService.retrieveProductInfoByProductName(product_name);
    }

    // Post Mapping for inserting Products
    @PostMapping("/insert-product")
    public void insertProduct(@RequestParam String product_name, double price, int stock, boolean is_vat_reduced) {
        databaseService.insertProduct(product_name, price, stock, is_vat_reduced);
    }
}
