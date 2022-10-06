package viewAndController;

import Exceptions.CustomerException;
import database.CityDB;
import database.CountryDB;
import database.CustomerDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.City;
import models.Country;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable {
    @FXML
    private TextField addressTwo;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField customerName;
    @FXML
    private TextField addressOne;
    @FXML
    private ComboBox<City> city;
    @FXML
    private ComboBox<Country> country;
    @FXML
    private TextField phone;
    @FXML
    private TextField postalCode;

    Stage stage;
    Parent scene;
    static boolean isNewCustomer;
    private Customer selectedCustomer = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isNewCustomer = CustomerController.isIsNewCustomer();
        country.getItems().addAll(CountryDB.getCountries());
        if (!isNewCustomer) {
            selectedCustomer = CustomerController.getSelectedCustomer();
            customerName.setText(selectedCustomer.getCustomerName());
            addressOne.setText(selectedCustomer.getAddress());
            phone.setText(selectedCustomer.getPhone());
            postalCode.setText(selectedCustomer.getPostalCode());
            country.setValue(selectedCustomer.getCustomerCountry());
            city.getItems().clear();
            city.getItems().addAll(CityDB.getCitiesForCountry(selectedCustomer.getCustomerCountry().getCountryId()));
            city.setValue(selectedCustomer.getCity());
        }
    }

    @FXML
    public void saveHandler(ActionEvent actionEvent) throws IOException, CustomerException {

        String phoneValue = phone.getText();
        boolean isDigit = true;
        for (int i = 0; i < phoneValue.length(); i++) {
            if (!Character.isDigit(phoneValue.charAt(i))) {
                isDigit = false;
                break;
            }
        }

        String customerNameText = customerName.getText();

        if (customerNameText.isEmpty() ||
                addressOne.getText().isEmpty() ||
                phone.getText().isEmpty() ||
                postalCode.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Customer");
            alert.setContentText("Please Fill In All Information.");
            alert.showAndWait();
            //throw new CustomerException("Null Exception Error");

        } else if (!isDigit) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Appointment Exception");
            alert.setContentText("Phone number must be digits.");
            alert.showAndWait();
            //throw new CustomerException("Null Exception Error");

        } else {
            String address = addressOne.getText();
            int cityId = city.getSelectionModel().getSelectedItem().getCityId();
            String phoneText = phone.getText();
            String postalCodeText = postalCode.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WGU Scheduling App");
            alert.setHeaderText("Add Customer");
            alert.setContentText("Are you sure you want to add/modify this customer?");
            alert.showAndWait();

            if (isNewCustomer) {
                CustomerDB.addCustomer(address, cityId, postalCodeText, phoneText, customerNameText);
            } else {
                CustomerDB.updateCustomer(selectedCustomer.getCustomerId(), selectedCustomer.getAddressId(), address, cityId, postalCodeText, phoneText,
                        customerNameText);
            }

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/viewAndController/customer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    public void cancelHandler(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/viewAndController/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    public void handleCountry(ActionEvent actionEvent) {
        city.getItems().clear();
        city.getItems().addAll(CityDB.getCitiesForCountry(country.getValue().getCountryId()));
        city.setValue(null);
    }
}
