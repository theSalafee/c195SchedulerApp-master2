package viewAndController;

import database.AppointmentDB;
import database.UserDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import models.Appointment;
import models.User;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Login implements Initializable {

    public static User loggedUser = new User();
    @FXML
    public Label errorText;
    @FXML
    public TextField userName;
    @FXML
    public TextField password;
    @FXML
    public Button loginBtn;
    @FXML
    public Label lblUsername;
    @FXML
    public Label lblPassword;
    ResourceBundle rb;
    Locale userLocale;
    Logger userLog = Logger.getLogger("userlog.txt");
    Appointment upcomingAppt = null;

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws Exception {
        String user = userName.getText();
        String pass = password.getText();
        loggedUser.setUserName(user);
        loggedUser.setPassword(pass);

        FileHandler userLogFH = new FileHandler("userlog.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        userLogFH.setFormatter(sf);
        userLog.addHandler(userLogFH);
        userLog.setLevel(Level.INFO);

        try {
            ObservableList<User> userLoginInfo = UserDB.getActiveUsers();
            //Lambda
            boolean correctLogin = false;
            // userLoginInfo.forEach((u) ->{
            for (User u : userLoginInfo) {
                if (loggedUser.getUserName().equals(u.getUserName()) && loggedUser.getPassword().equals(u.getPassword())) {
                    correctLogin = true;
                    loggedUser.setUserId(u.getUserId());
                    break;
                }
            }
            if (correctLogin) {
                userLog.log(Level.INFO, "User: {0} logged in.", loggedUser.getUserName());
                FXMLLoader apptCalLoader = new FXMLLoader(AppointmentController.class.getResource("mainMenu.fxml"));
                Parent apptCalScreen = apptCalLoader.load();
                Scene apptCalScene = new Scene(apptCalScreen);
                Stage apptCalStage = new Stage();
                apptCalStage.setTitle("Appointment Calendar");
                apptCalStage.setScene(apptCalScene);
                apptCalStage.show();
                Stage loginStage = (Stage) loginBtn.getScene().getWindow();
                loginStage.close();
                if (upcomingAppt != null && !(upcomingAppt.getAppointmentId() == 0)) { // add upcomingAppt to mainMenue controller
                    Alert apptAlert = new Alert(Alert.AlertType.INFORMATION);
                    apptAlert.setTitle("Upcoming Appointment Reminder");
                    apptAlert.setHeaderText("You have an upcoming appointment!");
                    apptAlert.setContentText("You have an appointment scheduled"
                            + "\non " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
                            + "\nat " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL))
                            + " with client " + upcomingAppt.getCustomer().getCustomerName() + ".");
                    apptAlert.showAndWait();
                    if (apptAlert.getResult() == ButtonType.OK) {
                        userLog.log(Level.INFO, "User: {0} logged in.", loggedUser.getUserName());
                        loginStage.close();
                        apptCalStage.setTitle("Appointment Calendar");
                        apptCalStage.setScene(apptCalScene);
                        apptCalStage.show();
                    } else {
                        apptAlert.close();
                    }
                }
            } else {
                this.errorText.setText(this.rb.getString("lblErrorAlert") + ".");
                this.errorText.setTextFill(Paint.valueOf("RED"));
                userLog.log(Level.WARNING, "Invalid credentials entered! User: {0}", loggedUser.getUserName());
            }

            ObservableList<Appointment> apps = FXCollections.observableArrayList();
            Appointment upcomingAppt = null;
            if (!apps.isEmpty()) {
                upcomingAppt = AppointmentDB.getUpcomingAppt().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userLocale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("LocaleLanguageFiles/rb", this.userLocale);
        this.lblUsername.setText(this.rb.getString("userName") + ":");
        this.lblPassword.setText(this.rb.getString("password") + ":");
        this.userName.setPromptText(this.rb.getString("usernamePrompt"));
        this.password.setPromptText(this.rb.getString("passwordPrompt"));
        this.loginBtn.setText(this.rb.getString("btnLoginText"));
    }
}
