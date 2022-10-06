package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.City;
import models.Country;
import models.Customer;

import static viewAndController.Login.loggedUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DbConnection.conn;

public class CustomerDB {

    public static ObservableList<Customer> getActiveCustomers() {
        ObservableList<Customer> activeCustomers = FXCollections.observableArrayList();
        String getActiveCustomersSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE active=1 AND customer.addressId = address.addressId " +
                "AND address.cityId = city.cityId and city.countryId = country.countryId";
        try {
            PreparedStatement stmt = conn.prepareStatement(getActiveCustomersSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("address.addressId");
                int active = rs.getInt("active");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city"), rs.getInt("country.countryId"));
                String address = rs.getString("address");
                String address2 = rs.getString("address.address2");
                Country customerCountry = new Country(rs.getInt("countryId"), rs.getString("country"));
                Customer activeCustomer = new Customer(customerId, customerName, addressId, phone, postalCode, city, address, customerCountry, active);
                activeCustomers.add(activeCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeCustomers;
    }

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCusts = FXCollections.observableArrayList();
        String getAllCustsSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE active=1 AND customer.addressId = address.addressId " +
                "AND address.cityId = city.cityId and city.countryId = country.countryId";

        try {
            PreparedStatement stmt = conn.prepareStatement(getAllCustsSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("address.addressId");
                int active = rs.getInt("active");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city"), rs.getInt("country.countryId"));
                String address = rs.getString("address");
                Country customerCountry = new Country(rs.getInt("countryId"), rs.getString("country"));
                Customer allCustomers = new Customer(customerId, customerName, addressId, phone, postalCode, city, address, customerCountry, active);
                allCusts.add(allCustomers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCusts;
    }

    public static Customer getActiveCustomerById(int customerId) {
        String getCustomerByIdSQL = "SELECT * FROM customer, address, city, country " +
                "WHERE customer.customerId = ? AND active=1 AND customer.addressId = address.addressId AND address.cityId = city.cityId and city.countryId = country.countryId";

        Customer c = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(getCustomerByIdSQL);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                String phone = rs.getString("phone");
                String postalCode = rs.getString("postalCode");
                City city = new City(rs.getInt("city.cityId"), rs.getString("city.city"), rs.getInt("city.countryId"));
                String address = rs.getString("address");
                Country country = new Country(rs.getInt("countryId"), rs.getString("country"));
                c = new Customer(customerId, customerName, addressId, phone, postalCode, city, address, country, 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    private static int getMaxCustomerId() {
        int maxCustomerId = 0;
        String maxCustomerIdSQL = "SELECT MAX(customerId) FROM customer";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(maxCustomerIdSQL);

            if (rs.next()) {
                maxCustomerId = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return maxCustomerId + 1;
    }

    public static void addCustomer(String address, int cityId, String postalCode, String phone, String customerName) {
        try {
            String addAddressSQL = String.join(" ",
                    "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)",
                    " VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?)");

            PreparedStatement stmt1 = conn.prepareStatement(addAddressSQL);

            stmt1.setString(1, address);
            stmt1.setString(2, "n/a");
            stmt1.setInt(3, cityId);
            stmt1.setString(4, postalCode);
            stmt1.setString(5, phone);
            stmt1.setString(6, loggedUser.getUserName());
            stmt1.setString(7, loggedUser.getUserName());
            stmt1.executeUpdate();

            String selectSQL = "SELECT LAST_INSERT_ID() FROM address";
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            rs.next();
            int addressId = rs.getInt(1);

            String addCustomerSQL = String.join(" ",
                    "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)",
                    "VALUES (?, ?, 1, NOW(), ?, NOW(), ?)");

            PreparedStatement stmt = conn.prepareStatement(addCustomerSQL);
            //stmt.setInt(1, customerId);
            stmt.setString(1, customerName);
            stmt.setInt(2, addressId);
            stmt.setString(3, loggedUser.getUserName());
            stmt.setString(4, loggedUser.getUserName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(int customerId, int addressId, String address, int cityId, String postalCode, String phone, String customerName) {
        try {

            String updateAddress = "UPDATE address SET address= ?, postalCode= ?, phone = ?, cityId = ?, lastUpdate=NOW() WHERE addressId = ?";
            PreparedStatement stmt = conn.prepareStatement(updateAddress);
            stmt.setString(1, address);
            stmt.setString(2, postalCode);
            stmt.setString(3, phone);
            stmt.setInt(4, cityId);
            stmt.setInt(5, addressId);
            stmt.execute();

            String updateCustomer = "UPDATE customer SET customerName= ?, lastUpdate=NOW()  WHERE customerId = ?";
            stmt = conn.prepareStatement(updateCustomer);
            stmt.setString(1, customerName);
            stmt.setInt(2, customerId);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer customer) {
        String deleteAddressSQL = "DELETE FROM appointment WHERE customerId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(deleteAddressSQL);
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String deleteCustomerSQL = "DELETE  FROM customer WHERE customerId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(deleteCustomerSQL);
            stmt.setInt(1, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
