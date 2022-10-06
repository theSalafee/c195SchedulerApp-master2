package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;

import java.sql.*;

public class UserDB {

    private final static Connection conn = DbConnection.conn;

    public UserDB(){}

    public static ObservableList<User> getActiveUsers(){
        ObservableList<User> activeUsers = FXCollections.observableArrayList();
        String getActiveUsers = "SELECT * FROM user WHERE active = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(getActiveUsers);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User activeUser = new User();
                activeUser.setUserId(rs.getInt("userId"));
                activeUser.setUserName(rs.getString("userName"));
                activeUser.setPassword(rs.getString("password"));
                activeUser.setActive(rs.getBoolean("active"));
                activeUsers.add(activeUser);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return activeUsers;
    }

    public static User getUserById(int userId) {
        String getUserByIdSQL = "SELECT * FROM user WHERE userId=?";
        User user = new User();

        try {
            PreparedStatement stmt = conn.prepareStatement(getUserByIdSQL);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
