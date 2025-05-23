package de.mmbbs.Kassensystem.service;

import de.mmbbs.Kassensystem.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DatabaseService {

    private final DAO dao;

    @Autowired
    public DatabaseService(DAO dao) {
        this.dao = dao;
    }

    public void insertProduct(String product_name, double price, double unit_stock, double weight_stock, boolean is_vat_reduced, boolean is_unit) {
        dao.insertProduct(product_name, price, unit_stock, weight_stock, is_vat_reduced, is_unit);
    }

    public Map<String, Object> retrieveProductInfoByProductName(String product_name) {
        try (ResultSet rs = dao.retrieveProductInformation(product_name)) {
            Map<String, Object> data = new HashMap<>();
            if (rs.next()) {
                data.put("ID", rs.getInt("ID"));
                data.put("PRODUCT_NAME", rs.getString("PRODUCT_NAME"));
                data.put("PRICE", rs.getDouble("PRICE"));
                data.put("UNIT_STOCK", rs.getDouble("UNIT_STOCK"));
                data.put("WEIGHT_STOCK", rs.getDouble("WEIGHT_STOCK"));
                data.put("IS_VAT_REDUCED", rs.getBoolean("IS_VAT_REDUCED"));
                data.put("IS_UNIT_NOT_WEIGHT", rs.getBoolean("IS_UNIT_NOT_WEIGHT"));
                return data;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStock(String product_name, double stock_change, boolean increase) {
        // Logic to update either UNIT_STOCK or WEIGHT_STOCK depending on IS_UNIT_NOT_WEIGHT
        // This will be added if DAO has an update method for stock
    }

    public double calculateTotal(String product_name, double quantity) {
        try (ResultSet rs = dao.retrieveProductInformation(product_name)) {
            if (rs.next()) {
                double price = rs.getDouble("PRICE");
                boolean isVatReduced = rs.getBoolean("IS_VAT_REDUCED");
                boolean isUnit = rs.getBoolean("IS_UNIT_NOT_WEIGHT");
                double stock = isUnit ? rs.getDouble("UNIT_STOCK") : rs.getDouble("WEIGHT_STOCK");

                if (quantity > stock) throw new IllegalArgumentException("Not enough stock.");

                double vatRate = isVatReduced ? 0.07 : 0.19;
                double net = price * quantity;
                return net * (1 + vatRate);
            } else {
                throw new IllegalArgumentException("Product not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}