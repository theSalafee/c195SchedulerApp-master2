package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.City;
import models.Country;
import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.DbConnection.conn;

public class CountryDB {
    public static ObservableList getCountries() {

        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM country";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("countryId");
                String country = rs.getString("country");
               Country c = new Country(countryId, country);
               countries.add(c);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
