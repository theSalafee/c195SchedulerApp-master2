package viewAndController;

import com.sun.jdi.connect.spi.Connection;
import database.CustomerDB;
import database.DbConnection;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.City;
import models.Country;
import models.Customer;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.DbConnection.conn;

/**
 * FXML Controller class
 *
 * @author Naasir Bush
 */
public class CustomerController implements Initializable {
    @FXML
    public AnchorPane customerView;
    @FXML
    public Button backBtn;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Button modifyCustomerBtn;
    @FXML
    public Button deleteCustomerBtn;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableColumn<Customer, String> customerName;
    @FXML
    public TableColumn<Customer, String> customerAddress1;
    @FXML
    public TableColumn<Customer, String> customerCity;
    @FXML
    public TableColumn<Customer, String> customerCountry;
    @FXML
    public TableColumn<Customer, String> customerPhone;
    @FXML
    public TableColumn<Customer, String> customerPostalCode;
    public TextField searchBox;

    ObservableList<Customer> customerTableSearch = FXCollections.observableArrayList();

    @FXML
    public TextField searchTable;

    Stage stage;
    Parent scene;

    static boolean isNewCustomer;
    static Customer selectedCustomer;



    public void backBtnHandler(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewAndController/mainMenu.fxml"));
        loader.load();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addHandler(ActionEvent actionEvent) throws IOException {

        isNewCustomer = true;

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        //stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/addCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void modifyHandler(ActionEvent actionEvent) {

        isNewCustomer = false;
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Something Went Wrong");
            alert.setContentText("Please Select a Customer.");
            alert.showAndWait();
            return;
        }

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        try {
            scene = FXMLLoader.load(getClass().getResource("/viewAndController/addCustomers.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deleteHandler(ActionEvent actionEvent) {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Something Went Wrong");
            alert.setContentText("Please Select a Customer.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("WGU Scheduling App");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Are you sure you want to delete this Customer");
        alert.showAndWait();

        CustomerDB.deleteCustomer(selectedCustomer);

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/viewAndController/customer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static boolean isIsNewCustomer() {
        return isNewCustomer;
    }

    public void convertCustomerString() {
    }

    public void setCustomerList() {
        customerTable.setItems(CustomerDB.getActiveCustomers());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setCustomerList();
        convertCustomerString();

        customerName.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });

        customerAddress1.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAddress());
        });

        customerCity.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCity().getCity());
        });

        customerPhone.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getPhone());
        });

        customerPostalCode.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getPostalCode());
        });

        customerCountry.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerCountry().getCountry());
        });

        String customerQuery = "SELECT * FROM customer WHERE customerName LIKE '%e%'";


    }

    public void onSearch(ActionEvent actionEvent) {
        customerTableSearch.clear();
        String e = searchBox.getText();
        String customerQuery = "SELECT * FROM customer, address, city, country WHERE active=1 AND customer.addressId = address.addressId AND address.cityId = city.cityId and city.countryId = country.countryId and customerName LIKE '%"+e+"%'";
        try {
            PreparedStatement stmt = conn.prepareStatement(customerQuery);
            ResultSet rs = stmt.executeQuery(customerQuery);

            while (rs.next()){
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
                customerTableSearch.add(activeCustomer);
            }
            customerTable.setItems(customerTableSearch);


        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
// comment
    public void onSearchKey(KeyEvent keyEvent) {
        onSearch(null);

    }
}
