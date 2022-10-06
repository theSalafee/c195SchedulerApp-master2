package models;

import Exceptions.AppointmentException;
import database.AppointmentDB;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class Appointment {

    private int appointmentId;
    private int customerId;
    private int userId;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Customer customer;
    Stage stage;
    Parent scene;

    public Appointment(int appointmentId, int customerId, int userId, String type, ZonedDateTime start, ZonedDateTime end, Customer customer) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer = customer;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isValidInput() throws AppointmentException {
        if (this.customer == null) {
            throw new AppointmentException("There was no customer selected!");
        }

        if (this.type.equals("")) {
            throw new AppointmentException("You must enter a type!");
        }
        isValidTime();
        return true;
    }

    public boolean isValidTime() throws AppointmentException {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate apptStartDate = this.start.toLocalDate();
        LocalTime apptStartTime = this.start.toLocalTime();
        LocalDate apptEndDate = this.end.toLocalDate();
        LocalTime apptEndTime = this.end.toLocalTime();
        int weekDay = apptStartDate.getDayOfWeek().getValue();

        if (!apptStartDate.isEqual(apptEndDate)) {
            throw new AppointmentException("An appoinment can only be a single day!");
        }
        if (weekDay == 6 || weekDay == 7) {
            throw new AppointmentException("An appointment can only be scheduled on weekdays!");
        }
        if (apptStartTime.isBefore(midnight.plusHours(8))) {
            throw new AppointmentException("An appointment cannot be scheduled before normal business hours!");
        }
        if (apptEndTime.isAfter(midnight.plusHours(17))) {
            throw new AppointmentException("An appointment cannot be scheduled after normal business hours!");
        }
        if (apptStartDate.isBefore(LocalDate.now()) || apptStartTime.isBefore(LocalTime.MIDNIGHT)) {
            throw new AppointmentException("An appointment cannot be scheduled in the past!");
        }
        return true;
    }

    public boolean isNotOverlapping() throws AppointmentException {
        ObservableList<Appointment> overlappingAppt = AppointmentDB.getOverlappingAppts(this.start.toLocalDateTime(), this.end.toLocalDateTime());
        if (overlappingAppt.size() > 1) {
            throw new AppointmentException("An appointment cannot be scheduled at the same time as another appointment!");
        }
        return true;
    }
}
