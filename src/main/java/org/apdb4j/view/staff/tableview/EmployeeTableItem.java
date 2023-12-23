package org.apdb4j.view.staff.tableview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

/**
 * An employee representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
@ToString
@EqualsAndHashCode
public final class EmployeeTableItem implements TableItem {

    private final StringProperty staffID;
    private final StringProperty nationalID;
    private final StringProperty name;
    private final StringProperty surname;
    private final ObjectProperty<LocalDate> dob;
    private final StringProperty birthplace;
    private final StringProperty gender;
    private final StringProperty role;
    private final BooleanProperty admin;
    private final DoubleProperty salary;
    private final StringProperty email;

    /**
     * Default constructor.
     * @param staffID the employee's staff ID
     * @param nationalID the employee's national ID
     * @param name the employee's name
     * @param surname the employee's surname
     * @param dob the employee's date of birth
     * @param birthplace the employee's birthplace
     * @param gender the employee's gender
     * @param role the employee's role
     * @param isAdmin {@code true} if the employee is an admin, {@code false} otherwise
     * @param salary the employee's salary
     * @param email the employee's email
     */
    public EmployeeTableItem(
            final String staffID,
            final String nationalID,
            final String name,
            final String surname,
            final LocalDate dob,
            final String birthplace,
            final String gender,
            final String role,
            final boolean isAdmin,
            final double salary,
            final String email
    ) {
        this.staffID = new SimpleStringProperty(staffID);
        this.nationalID = new SimpleStringProperty(nationalID);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.dob = new SimpleObjectProperty<>(dob);
        this.birthplace = new SimpleStringProperty(birthplace);
        this.gender = new SimpleStringProperty(gender);
        this.role = new SimpleStringProperty(role);
        this.admin = new SimpleBooleanProperty(isAdmin);
        this.salary = new SimpleDoubleProperty(salary);
        this.email = new SimpleStringProperty(email);
    }

    /**
     * Returns the employee's staff ID.
     * @return the employee's staff ID
     */
    public String getStaffID() {
        return staffID.get();
    }

    /**
     * Returns the employee's national ID.
     * @return the employee's national ID
     */
    public String getNationalID() {
        return nationalID.get();
    }

    /**
     * Returns the employee's name.
     * @return the employee's name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the employee's surname.
     * @return the employee's surname
     */
    public String getSurname() {
        return surname.get();
    }

    /**
     * Returns the employee's date of birth.
     * @return the employee's date of birth
     */
    public LocalDate getDob() {
        return dob.get();
    }

    /**
     * Returns the employee's birthplace.
     * @return the employee's birthplace
     */
    public String getBirthplace() {
        return birthplace.get();
    }

    /**
     * Returns the employee's gender.
     * @return the employee's gender
     */
    public String getGender() {
        return gender.get();
    }

    /**
     * Returns the employee's role.
     * @return the employee's role
     */
    public String getRole() {
        return role.get();
    }

    /**
     * Returns if the employee is an admin.
     * @return {@code true} if the employee is an admin,
     *         {@code false} otherwise
     */
    public boolean isAdmin() {
        return admin.get();
    }

    /**
     * Returns the employee's salary.
     * @return the employee's salary
     */
    public double getSalary() {
        return salary.get();
    }

    /**
     * Returns the employee's email.
     * @return the employee's email
     */
    public String getEmail() {
        return email.get();
    }

}
