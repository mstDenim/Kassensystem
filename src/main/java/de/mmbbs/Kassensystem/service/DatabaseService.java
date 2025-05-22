package de.mmbbs.Kassensystem.service;

import de.mmbbs.Kassensystem.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final DAO dao;

    @Autowired
    public DatabaseService(DAO dao) {
        this.dao = dao;
    }


    // when data is inserted the get request should when not completed open a new thread? (or to expensive?)
    // when api is called multiple times at the same time it should 'spawn' new threads?
    //
    //
    public void insertProduct(String product_name, double price, int stock, boolean is_vat_reduced) {
        dao.insertProduct(product_name, price, stock, is_vat_reduced);
    }

    public void retrieveProductInfoByProductName(String product_name) {
        dao.retrieveProductInformation(product_name);
    }
}