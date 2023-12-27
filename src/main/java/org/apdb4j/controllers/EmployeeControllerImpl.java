package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.core.managers.ContractManager;
import org.apdb4j.core.managers.StaffManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.EmployeeTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Record;
import org.jooq.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.STAFF;

/**
 * A staff controller specifically used for employees.
 */
public class EmployeeControllerImpl implements AdministrationController {

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
    @SuppressWarnings("unchecked")
    public <T extends TableItem> Collection<T> getData() {
        final List<T> data = new ArrayList<>();
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select(STAFF.asterisk().except(STAFF.ISEMPLOYEE), CONTRACTS.SALARY)
                        .from(STAFF)
                        .join(CONTRACTS)
                        .on(CONTRACTS.EMPLOYEENID.eq(STAFF.NATIONALID))
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        result.forEach(record -> {
            data.add((T) new EmployeeTableItem(record.get(STAFF.STAFFID),
                    record.get(STAFF.NATIONALID),
                    record.get(STAFF.NAME),
                    record.get(STAFF.SURNAME),
                    record.get(STAFF.DOB),
                    record.get(STAFF.BIRTHPLACE),
                    record.get(STAFF.GENDER),
                    record.get(STAFF.ROLE),
                    record.get(STAFF.ISADMIN).equals((byte) 1),
                    record.get(CONTRACTS.SALARY).doubleValue(),
                    record.get(STAFF.EMAIL)));
        });
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final EmployeeTableItem employee = (EmployeeTableItem) item;
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
                        // TODO
                        ContractManager.signNewContract("C-006",
                                employee.getNationalID(),
                                "MRARSS77E15A944I",
                                LocalDate.parse("2023-10-05"),
                                LocalDate.parse("2023-11-01"),
                                null,
                                employee.getSalary(),
                                "a");
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
    public <T extends TableItem> T editData(final T item) {
        // TODO
        return item;
    }

}
