package de.mmbbs.Kassensystem.controller;

import de.mmbbs.Kassensystem.model.Product;
import de.mmbbs.Kassensystem.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class Controller {

    private final DatabaseService databaseService;

    @Autowired
    public Controller(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProduct(@RequestBody Product product) {
        // Manual validation
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Product name is required");
        }
        if (product.getPrice() < 0) {
            return ResponseEntity.badRequest().body("Price must be non-negative");
        }
        if (product.getStock() < 0) {
            return ResponseEntity.badRequest().body("Stock must be non-negative");
        }
        if (product.isVatReduced() == null) {
            return ResponseEntity.badRequest().body("VAT status must be specified");
        }
        if (product.isUnit() == null) {
            return ResponseEntity.badRequest().body("Unit type must be specified");
        }

        try {
            double unitStock = product.isUnit() ? product.getStock() : 0;
            double weightStock = product.isUnit() ? 0 : product.getStock();

            databaseService.insertProduct(
                    product.getProductName(),
                    product.getPrice(),
                    unitStock,
                    weightStock,
                    product.isVatReduced(),
                    product.isUnit()
            );
            return ResponseEntity.ok("Product inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error inserting product: " + e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getProductInfo(@RequestParam String name) {
        Map<String, Object> productData = databaseService.retrieveProductInfoByProductName(name);
        return productData != null ? ResponseEntity.ok(productData) : ResponseEntity.notFound().build();
    }

    @GetMapping("/calculate")
    public ResponseEntity<String> calculateTotal(@RequestParam String name, @RequestParam double quantity) {
        try {
            double total = databaseService.calculateTotal(name, quantity);
            return ResponseEntity.ok("Total including VAT: " + total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
