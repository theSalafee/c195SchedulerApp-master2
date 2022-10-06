package models;

import Exceptions.CustomerException;
import javafx.scene.control.TableColumn;

public class Customer {
    public Customer(int customerId, String customerName, int addressId, String phone, String postalCode, City city, String address, Country customerCountry, int active) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.phone = phone;
        this.postalCode = postalCode;
        this.city = city;
        this.address = address;
        this.customerCountry = customerCountry;
        this.active = active;
    }

    private int customerId;
    private String customerName;
    private int addressId;
    private String phone;
    private String postalCode;
    private City city;
    private String address;

//    public Customer(TableColumn<Customer, String> customerName, TableColumn<Customer, String> customerAddress1, TableColumn<Customer, String> customerCity, TableColumn<Customer, String> customerPhone, TableColumn<Customer, String> customerCountry) {
//    }

    @Override
    public String toString() {
        return customerName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    private int active;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Country getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(Country customerCountry) {
        this.customerCountry = customerCountry;
    }

    private Country customerCountry;


    public static boolean isValidInput(Customer Cust, Address custAddress, City custCity, Country custCountry) throws CustomerException {
        if (Cust.getCustomerName().equals("")) {
            throw new CustomerException("You must enter a customer!");
        }
        if (custAddress.getAddress().equals("")) {
            throw new CustomerException("You must enter an address in the address field!");
        }
        if (custAddress.getPhone().equals("")) {
            throw new CustomerException("You must enter a phone number!");
        }
        if (custAddress.getPostalCode().equals("")) {
            throw new CustomerException("You must enter a postal code!");
        }
        if (custCity.getCity().equals("")) {
            throw new CustomerException("You must enter a city!");
        }
        if (custCountry.getCountry().equals("")) {
            throw new CustomerException("You must select a country!");
        }
        return true;
    }
}
