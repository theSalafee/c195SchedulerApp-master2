package viewAndController;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Naasir Bush
 */
public class Customer {
    public AnchorPane customerView;
    public Button backBtn;

    public AnchorPane getCustomerView() {
        return customerView;
    }

    public void setCustomerView(AnchorPane customerView) {
        this.customerView = customerView;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public Button getAddCustomerBtn() {
        return addCustomerBtn;
    }

    public void setAddCustomerBtn(Button addCustomerBtn) {
        this.addCustomerBtn = addCustomerBtn;
    }

    public Button getModifyCustomerBtn() {
        return modifyCustomerBtn;
    }

    public void setModifyCustomerBtn(Button modifyCustomerBtn) {
        this.modifyCustomerBtn = modifyCustomerBtn;
    }

    public Button getDeleteCustomerBtn() {
        return deleteCustomerBtn;
    }

    public void setDeleteCustomerBtn(Button deleteCustomerBtn) {
        this.deleteCustomerBtn = deleteCustomerBtn;
    }

    public TableView getCustomerTable() {
        return customerTable;
    }

    public void setCustomerTable(TableView customerTable) {
        this.customerTable = customerTable;
    }

    public TableColumn getCustName() {
        return custName;
    }

    public void setCustName(TableColumn custName) {
        this.custName = custName;
    }

    public TableColumn getCustAddress1() {
        return custAddress1;
    }

    public void setCustAddress1(TableColumn custAddress1) {
        this.custAddress1 = custAddress1;
    }

    public TableColumn getCustAddress2() {
        return custAddress2;
    }

    public void setCustAddress2(TableColumn custAddress2) {
        this.custAddress2 = custAddress2;
    }

    public TableColumn getCustCity() {
        return custCity;
    }

    public void setCustCity(TableColumn custCity) {
        this.custCity = custCity;
    }

    public TableColumn getCustCounty() {
        return custCounty;
    }

    public void setCustCounty(TableColumn custCounty) {
        this.custCounty = custCounty;
    }

    public TableColumn getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(TableColumn custPhone) {
        this.custPhone = custPhone;
    }

    public TableColumn getCustPostalCode() {
        return custPostalCode;
    }

    public void setCustPostalCode(TableColumn custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    public Button addCustomerBtn;
    public Button modifyCustomerBtn;
    public Button deleteCustomerBtn;
    public TableView customerTable;
    public TableColumn custName;
    public TableColumn custAddress1;
    public TableColumn custAddress2;
    public TableColumn custCity;
    public TableColumn custCounty;
    public TableColumn custPhone;
    public TableColumn custPostalCode;


    // added a comment

    public void backBtnHandler(ActionEvent actionEvent) {
    }

    public void addHandler(ActionEvent actionEvent) {
    }

    public void modifyHandler(ActionEvent actionEvent) {
    }

    public void deleteHandler(ActionEvent actionEvent) {
    }
}
