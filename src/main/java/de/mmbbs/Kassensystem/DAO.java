package de.mmbbs.Kassensystem;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DAO {

    // Database URL and Credentials
    static final String URL = "jdbc:h2:~/Workspaces/IntelliJ/Kassensystem/Kassensystem";
    static final String USER = "sa";
    static final String PASSWORD = "";

    // Database Table DDL
    // Maybe stueck & kg booleans

    static final String PRODUCTS_TABLE_INIT =
                    "create table PRODUCTS (\n" +
                    "    ID BIGINT auto_increment,\n" +
                    "    PRODUCT_NAME CHARACTER VARYING(150) not null,\n" +
                    "    TIMESTAMP TIMESTAMP not null,\n" +
                    "    PRICE DOUBLE not null,\n" +
                    "    STOCK BIGINT not null, \n" +
                    "    IS_VAT_REDUCED BOOLEAN not null,\n" +
                    "    CONSTRAINT FILES_PK\n" +
                    "    PRIMARY KEY (ID),\n" +
                    "    CONSTRAINT PRODUCT_NAME_UNIQUE UNIQUE (PRODUCT_NAME)\n" +
                    ");";

    // Queries
    static final String INSERT_PRODUCT = "INSERT INTO PRODUCTS (PRODUCT_NAME, PRICE, STOCK, IS_VAT_REDUCED, TIMESTAMP) VALUES (?,?,?,?,?)";

    static final String RETRIEVE_PRODUCT_INFORMATION = "SELECT * FROM PRODUCTS WHERE PRODUCT_NAME = ?";
    // Prolly redundant
    static final String RETRIEVE_CURRENT_STOCK = "SELECT * FROM CALLING_DATA WHERE DOCUMENT_NAME = ?";

    static final String RETRIEVE_STOCK_BY_PRODUCT_NAME = "SELECT * FROM CALLING_DATA WHERE DOCUMENT_NAME = ?";

    static final String UPDATE_STOCK_COUNT = "have to update here";

    // Methods
    public void initDatabaseTable() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(PRODUCTS_TABLE_INIT);
            }
        } catch (Exception e) {
            System.out.println("the database couldn't be initialized");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void insertProduct(String product_name, double price, int stock,  boolean is_vat_reduced) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Timestamp currentSqlDate = new Timestamp(System.currentTimeMillis());
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
                preparedStatement.setString(1, product_name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, stock);
                preparedStatement.setBoolean(4, is_vat_reduced);
                preparedStatement.setTimestamp(5, currentSqlDate);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void retrieveProductInformation(String product_name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_PRODUCT_INFORMATION)) {
                preparedStatement.setString(1, product_name);
                preparedStatement.executeQuery();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String productName = resultSet.getString("PRODUCT_NAME");
                        double price = resultSet.getDouble("PRICE");
                        boolean vat_reduced = resultSet.getBoolean("IS_VAT_REDUCED");
                        System.out.println("ID: " + id + "\n"
                                + "Product Name: " + productName + "\n"
                                + "Price : " + price + "\n"
                                + "Is Vat reduced (7% MwSt): " + vat_reduced + "\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
