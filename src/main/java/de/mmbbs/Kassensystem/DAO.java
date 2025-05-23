package de.mmbbs.Kassensystem;

import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class DAO {

    static final String URL = "jdbc:h2:~/Workspaces/IntelliJ/Kassensystem/Kassensystem";
    static final String USER = "sa";
    static final String PASSWORD = "";

    static final String PRODUCTS_TABLE_INIT =
            "create table PRODUCTS (\n" +
                    "    ID BIGINT auto_increment,\n" +
                    "    PRODUCT_NAME CHARACTER VARYING(150) not null,\n" +
                    "    TIMESTAMP TIMESTAMP not null,\n" +
                    "    PRICE DOUBLE not null,\n" +
                    "    UNIT_STOCK DOUBLE,\n" +
                    "    WEIGHT_STOCK DOUBLE,\n" +
                    "    IS_VAT_REDUCED BOOLEAN not null,\n" +
                    "    IS_UNIT_NOT_WEIGHT BOOLEAN not null,\n" +
                    "    CONSTRAINT FILES_PK PRIMARY KEY (ID),\n" +
                    "    CONSTRAINT PRODUCT_NAME_UNIQUE UNIQUE (PRODUCT_NAME)\n" +
                    ");";

    static final String INSERT_PRODUCT = "INSERT INTO PRODUCTS (PRODUCT_NAME, PRICE, UNIT_STOCK, WEIGHT_STOCK, IS_VAT_REDUCED, IS_UNIT_NOT_WEIGHT, TIMESTAMP) VALUES (?, ?, ?, ?, ?, ?, ?)";

    static final String RETRIEVE_PRODUCT_INFORMATION = "SELECT * FROM PRODUCTS WHERE PRODUCT_NAME = ?";

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

    public void insertProduct(String product_name, double price, double unit_stock, double weight_stock, boolean is_vat_reduced, boolean is_unit) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Timestamp currentSqlDate = new Timestamp(System.currentTimeMillis());
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
                preparedStatement.setString(1, product_name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setDouble(3, unit_stock);
                preparedStatement.setDouble(4, weight_stock);
                preparedStatement.setBoolean(5, is_vat_reduced);
                preparedStatement.setBoolean(6, is_unit);
                preparedStatement.setTimestamp(7, currentSqlDate);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet retrieveProductInformation(String product_name) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_PRODUCT_INFORMATION);
            preparedStatement.setString(1, product_name);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
