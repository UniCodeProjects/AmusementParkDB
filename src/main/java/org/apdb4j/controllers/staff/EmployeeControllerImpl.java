package org.apdb4j.controllers.staff;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apdb4j.core.managers.StaffManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.util.view.AlertBuilder;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.tools.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apdb4j.db.Tables.*;
import static org.jooq.impl.DSL.*;

/**
 * A staff controller specifically used for employees.
 */
public class EmployeeControllerImpl implements EmployeeController {

    @Setter
    private static TableView<ContractTableItem> contractTableView;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return getEmployeeData(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T employeeItem, final T contractItem) {
        final EmployeeTableItem employee = (EmployeeTableItem) employeeItem;
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        StaffManager.hireNewStaffMember(employee.getNationalID(),
                                employee.getStaffID(),
                                employee.getEmail(),
                                employee.getName(),
                                employee.getSurname(),
                                employee.getDob(),
                                employee.getBirthplace(),
                                employee.getGender().charAt(0),
                                employee.getRole(),
                                employee.isAdmin(),
                                !employee.isAdmin(),
                                "");
                        ContractTableItem contract = null;
                        try {
                            contract = (ContractTableItem) new ContractControllerImpl().addData(contractItem);
                        } catch (final SQLException e) {
                            new AlertBuilder().setAlertType(Alert.AlertType.ERROR)
                                    .setContentText(new ContractControllerImpl().getErrorMessage().orElse(""))
                                    .show();
                        }
                        // Final var for lambda usage.
                        final ContractTableItem finalContract = contract;
                        Platform.runLater(() -> Objects.requireNonNull(contractTableView).getItems().add(finalContract));
                    });
                    return 1;
                })
                .closeConnection();
        return employeeItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final EmployeeTableItem employee = (EmployeeTableItem) item;
        new QueryBuilder().createConnection()
                .queryAction(db -> {
                    db.transaction(configuration -> {
                        configuration.dsl()
                                .update(ACCOUNTS)
                                .set(ACCOUNTS.PERMISSIONTYPE, employee.isAdmin() ? "Admin" : "Staff")
                                .where(ACCOUNTS.EMAIL.eq(employee.getEmail()))
                                .execute();
                        configuration.dsl()
                                .update(STAFF)
                                .set(STAFF.NATIONALID, employee.getNationalID())
                                .set(STAFF.NAME, employee.getName())
                                .set(STAFF.SURNAME, employee.getSurname())
                                .set(STAFF.DOB, employee.getDob())
                                .set(STAFF.BIRTHPLACE, employee.getBirthplace())
                                .set(STAFF.GENDER, employee.getGender())
                                .set(STAFF.ROLE, StringUtils.defaultIfEmpty(employee.getRole(), null))
                                .set(STAFF.ISADMIN, employee.isAdmin() ? Byte.valueOf((byte) 1) : Byte.valueOf((byte) 0))
                                .set(STAFF.ISEMPLOYEE, !employee.isAdmin() ? Byte.valueOf((byte) 1) : Byte.valueOf((byte) 0))
                                .where(STAFF.STAFFID.eq(employee.getStaffID()))
                                .execute();
                    });
                    return 1;
                })
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T fire(final T employeeItem) {
        final EmployeeTableItem employee = (EmployeeTableItem) employeeItem;
        StaffManager.fireStaffMember(employee.getNationalID(), "");
        return employeeItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> Collection<T> getFiredData() {
        return getEmployeeData(true);
    }

    /**
     * {@inheritDoc}
     * <br>In this instance it filters the employees based on their name and surname.
     * @implNote This method only filters employees that have not been fired yet
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String s) {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.asterisk().except(STAFF.ISEMPLOYEE), CONTRACTS.SALARY)
                        .from(STAFF)
                        .leftJoin(CONTRACTS)
                        .on(CONTRACTS.EMPLOYEENID.eq(STAFF.NATIONALID))
                        .where(CONTRACTS.ENDDATE.isNull())
                        .or(STAFF.ISADMIN.isTrue())
                        .and(concat(field(STAFF.NAME), inline(" "), field(STAFF.SURNAME)).containsIgnoreCase(s))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return extractEmployeeData(result);
    }

    private <T extends TableItem> @NonNull List<T> getEmployeeData(final boolean fired) {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.asterisk().except(STAFF.ISEMPLOYEE), CONTRACTS.SALARY)
                        .from(STAFF)
                        .leftJoin(CONTRACTS)
                        .on(CONTRACTS.EMPLOYEENID.eq(STAFF.NATIONALID))
                        .where(fired ? CONTRACTS.ENDDATE.isNotNull() : CONTRACTS.ENDDATE.isNull())
                        .or(STAFF.ISADMIN.isTrue())
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return extractEmployeeData(result);
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> List<T> extractEmployeeData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new EmployeeTableItem(record.get(STAFF.STAFFID),
                record.get(STAFF.NATIONALID),
                record.get(STAFF.NAME),
                record.get(STAFF.SURNAME),
                record.get(STAFF.DOB),
                record.get(STAFF.BIRTHPLACE),
                record.get(STAFF.GENDER),
                record.get(STAFF.ROLE),
                record.get(STAFF.ISADMIN).equals((byte) 1),
                ObjectUtils.getIfNull(record.get(CONTRACTS.SALARY), () -> Double.NaN).doubleValue(),
                record.get(STAFF.EMAIL))));
        return data;
    }

}