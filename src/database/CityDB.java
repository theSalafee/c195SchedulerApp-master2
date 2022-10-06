package database;

import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.City;
import models.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static database.DbConnection.conn;
import static viewAndController.Login.loggedUser;

public class CityDB {

    public static ObservableList getCitiesForCountry(int countryId){
        String getCitySQL = "SELECT * FROM city WHERE countryId = ?";
        ObservableList<City> cities = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = conn.prepareStatement(getCitySQL);
            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
               City c = new City(rs.getInt("cityId"), rs.getString("city"), countryId);
               cities.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static int getCityId(String city) {
        String getCitySQL = "SELECT cityId FROM city WHERE city = ?";
        int cityId = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(getCitySQL);
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cityId = rs.getInt("cityId");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cityId;
    }

    public static City getCityById(int cityId) {
        String getCityByIdSQL = "SELECT * FROM city WHERE cityId = ?";
        City getCityById = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(getCityByIdSQL);
            stmt.setInt(1, cityId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int cityId1 = rs.getInt("cityId");
                String city = rs.getString("city");
                int countryId = rs.getInt("countryId");
                getCityById = new City(cityId1, city, countryId);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return getCityById;
    }

    private static int getMaxCityId() {
        int maxCityId = 0;
        String maxCityIdSQL = "SELECT MAX(cityId) FROM city";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(maxCityIdSQL);

            if (rs.next()) {
                maxCityId = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return maxCityId + 1;
    }

    public static City addCity(City city) {
        String addCitySQL = String.join(" ",
                "INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, NOW(), ?, NOW(), ?)");

        int cityId = getMaxCityId();
        try {
            PreparedStatement stmt = conn.prepareStatement(addCitySQL);
            stmt.setInt(1, cityId);
            stmt.setString(2, city.getCity());
            stmt.setInt(3, city.getCountryId());
            stmt.setString(4, loggedUser.getUserName());
            stmt.setString(5, loggedUser.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }
    public static void updateCity(City city) {
        String updateCitySQL = String.join(" ",
                "UPDATE city",
                "SET city=?, countryId=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE cityId=?");

        try {
            PreparedStatement stmt = conn.prepareStatement(updateCitySQL);
            stmt.setString(1, city.getCity());
            stmt.setInt(2, city.getCountryId());
            stmt.setString(3, loggedUser.getUserName());
            stmt.setInt(4, city.getCityId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCity(City city) {
        String deleteCitySQL = "DELETE FROM city WHERE cityId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(deleteCitySQL);
            stmt.setInt(1, city.getCityId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
