package database;

import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static database.DbConnection.conn;
import static viewAndController.Login.loggedUser;

public class AddressDB {

    public static int getAddressId(String address){

        String getAddressSQL = "SELECT addressId FROM address WHERE address = ?";
        int addressId = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(getAddressSQL);
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                addressId = rs.getInt("addressId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressId;
    }

    public static Address getAddressById(int addressId){
        String getAddressByIdSQL = "SELECT * FROM address WHERE addressId = ?";
        Address getAddById = new Address();

        try {
            PreparedStatement stmt = conn.prepareStatement(getAddressByIdSQL);
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                getAddById.setAddressId(rs.getInt("address"));
                getAddById.setAddress(rs.getString("address"));
                getAddById.setAddress2(rs.getString("address2"));
                getAddById.setCityId(rs.getInt("cityId"));
                getAddById.setPostalCode(rs.getString("postalCode"));
                getAddById.setPhone(rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getAddById;
    }

    private static int getMaxAddressId() {
        int maxAddressId = 0;
        String maxAddressIdSQL = "SELECT MAX(addressId) FROM address";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(maxAddressIdSQL);

            if(rs.next()){
                maxAddressId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxAddressId + 1;
    }

    public static void updateAddress(Address address) {
        String updateAddressSQL = String.join(" ",
                "UPDATE address",
                "SET address=?, address2=?, cityId=?, postalCode=?, phone=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE addressId=?");

        try{
            PreparedStatement stmt = conn.prepareStatement(updateAddressSQL);
            stmt.setString(1, address.getAddress());
            stmt.setString(2, address.getAddress2());
            stmt.setInt(3, address.getCityId());
            stmt.setString(4, address.getPostalCode());
            stmt.setString(5, address.getPhone());
            stmt.setString(6, loggedUser.getUserName());
            stmt.setInt(7, address.getAddressId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
        }
    }
    public void deleteAddress(Address address) {
        String deleteAddressSQL = "DELETE FROM address WHERE addressId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(deleteAddressSQL);
            stmt.setInt(1, address.getAddressId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
        }
    }
}
