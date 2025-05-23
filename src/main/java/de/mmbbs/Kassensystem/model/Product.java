package de.mmbbs.Kassensystem.model;


public class Product {

    private String productName;

    private double price;

    private double stock;

    private Boolean isVatReduced;

    private Boolean isUnit;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Boolean isVatReduced() {
        return isVatReduced;
    }

    public void setVatReduced(Boolean vatReduced) {
        isVatReduced = vatReduced;
    }

    public Boolean isUnit() {
        return isUnit;
    }

    public void setUnit(Boolean unit) {
        isUnit = unit;
    }
}
