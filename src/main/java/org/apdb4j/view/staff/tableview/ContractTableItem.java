package org.apdb4j.view.staff.tableview;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * A contract representation used by the table view in the GUI.
 * @see javafx.scene.control.TableView
 */
public class ContractTableItem implements TableItem {

    private final StringProperty id;
    private final StringProperty employeeNID;
    private final StringProperty employerNID;
    private final ObjectProperty<LocalDate> signedDate;
    private final ObjectProperty<LocalDate> beginDate;
    private final ObjectProperty<LocalDate> endDate;
    private final DoubleProperty salary;

    /**
     * Default constructor.
     * @param id the contract id
     * @param employeeNID the employee national ID
     * @param employerNID the employer national ID
     * @param signedDate the contract subscription date
     * @param beginDate the contract begin date
     * @param endDate the contract end date. Can be {@code null}
     * @param salary the salary specified by this contract
     */
    public ContractTableItem(final @NonNull String id,
                             final @NonNull String employeeNID,
                             final @NonNull String employerNID,
                             final @NonNull LocalDate signedDate,
                             final @NonNull LocalDate beginDate,
                             final LocalDate endDate,
                             final double salary) {
        this.id = new SimpleStringProperty(id);
        this.employeeNID = new SimpleStringProperty(employeeNID);
        this.employerNID = new SimpleStringProperty(employerNID);
        this.signedDate = new SimpleObjectProperty<>(signedDate);
        this.beginDate = new SimpleObjectProperty<>(beginDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.salary = new SimpleDoubleProperty(salary);
    }

    /**
     * Returns the contract ID.
     * @return the contract ID
     */
    public String getId() {
        return id.get();
    }

    /**
     * Returns the employee national ID.
     * @return the employee national ID
     */
    public String getEmployeeNID() {
        return employeeNID.get();
    }

    /**
     * Returns the employer national ID.
     * @return the employer national ID
     */
    public String getEmployerNID() {
        return employerNID.get();
    }

    /**
     * Returns the contract subscription date.
     * @return the contract subscription date
     */
    public LocalDate getSignedDate() {
        return signedDate.get();
    }

    /**
     * Returns the contract begin date.
     * @return the contract begin date
     */
    public LocalDate getBeginDate() {
        return beginDate.get();
    }

    /**
     * Returns the contract end date.
     * @return the contract end date. Can be {@code null}
     */
    public LocalDate getEndDate() {
        return endDate.get();
    }

    /**
     * Returns the salary specified by this contract.
     * @return the salary specified by this contract
     */
    public double getSalary() {
        return salary.get();
    }

}
