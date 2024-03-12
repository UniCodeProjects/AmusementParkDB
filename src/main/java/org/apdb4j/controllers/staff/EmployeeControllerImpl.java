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
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.STAFF;
import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;

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
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return getEmployeeData(CONTRACTS.ENDDATE.isNull()
                .or(CONTRACTS.ENDDATE.greaterThan(LocalDate.now()))
                .or(STAFF.ISADMIN.isTrue()));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final EmployeeTableItem employee = (EmployeeTableItem) item;
        final boolean queryResult = StaffManager.hireNewStaffMember(employee.getNationalID(),
                employee.getStaffID(),
                employee.getEmail(),
                employee.getName(),
                employee.getSurname(),
                employee.getDob(),
                employee.getBirthplace(),
                employee.getGender().charAt(0),
                employee.getRole(),
                employee.isAdmin(),
                !employee.isAdmin());
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while adding a new employee.");
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
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
                                !employee.isAdmin()
                        );
                        ContractTableItem contract = null;
                        try {
                            contract = (ContractTableItem) new ContractControllerImpl().addData(contractItem);
                        } catch (final DataAccessException e) {
                            new AlertBuilder(Alert.AlertType.ERROR)
                                    .setContentText(e.getCause().getMessage())
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
     * @throws org.jooq.exception.DataAccessException if query fails
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
     *
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Optional<T> fire(final T employeeItem) {
        final EmployeeTableItem employee = (EmployeeTableItem) employeeItem;
        try {
            StaffManager.fireStaffMember(employee.getNationalID());
        } catch (final DataAccessException e) {
            new AlertBuilder(Alert.AlertType.ERROR)
                    .setContentText(e.getMessage())
                    .show();
            return Optional.empty();
        }
        return Optional.of(employeeItem);
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getFiredData() {
        return getEmployeeData(CONTRACTS.ENDDATE.isNotNull().and(CONTRACTS.ENDDATE.lessOrEqual(LocalDate.now())));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if query fails
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
                        .where(CONTRACTS.ENDDATE.isNull()
                                .or(CONTRACTS.ENDDATE.greaterThan(LocalDate.now()))
                                .or(STAFF.ISADMIN.isTrue()))
                        .or(STAFF.ISADMIN.isTrue())
                        .and(concat(field(STAFF.NAME), inline(" "), field(STAFF.SURNAME)).containsIgnoreCase(s))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        return extractEmployeeData(result);
    }

    private <T extends TableItem> @NonNull List<T> getEmployeeData(final Condition condition) {
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.asterisk().except(STAFF.ISEMPLOYEE), CONTRACTS.SALARY)
                        .from(STAFF)
                        .leftJoin(CONTRACTS)
                        .on(CONTRACTS.EMPLOYEENID.eq(STAFF.NATIONALID))
                        .where(condition)
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
