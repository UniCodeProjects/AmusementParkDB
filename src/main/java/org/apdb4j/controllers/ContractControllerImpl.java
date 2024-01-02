package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.core.managers.ContractManager;
import org.apdb4j.core.permissions.AccessDeniedException;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.CONTRACTS;

/**
 * An administration controller specifically used for contracts.
 */
public class ContractControllerImpl implements AdministrationController {

    private String errorMessage;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends TableItem> Collection<T> getData() {
        final List<T> data = new ArrayList<>();
        final Result<Record> result = new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(CONTRACTS)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
        result.forEach(record -> data.add((T) new ContractTableItem(record.get(CONTRACTS.CONTRACTID),
                record.get(CONTRACTS.EMPLOYEENID),
                record.get(CONTRACTS.EMPLOYERNID),
                record.get(CONTRACTS.SUBSCRIPTIONDATE),
                record.get(CONTRACTS.BEGINDATE),
                record.get(CONTRACTS.ENDDATE),
                record.get(CONTRACTS.SALARY).doubleValue())));
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final ContractTableItem contract = (ContractTableItem) item;
        try {
            ContractManager.signNewContract(contract.getId(),
                    contract.getEmployeeNID(),
                    contract.getEmployerNID(),
                    contract.getSignedDate(),
                    contract.getBeginDate(),
                    contract.getEndDate(),
                    contract.getSalary(),
                    "");
        } catch (final AccessDeniedException e) {
            errorMessage = e.getMessage();
        }
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final ContractTableItem contract = (ContractTableItem) item;
        new QueryBuilder().createConnection()
                .queryAction(db -> db.update(CONTRACTS)
                        .set(CONTRACTS.EMPLOYEENID, contract.getEmployeeNID())
                        .set(CONTRACTS.EMPLOYERNID, contract.getEmployerNID())
                        .set(CONTRACTS.SUBSCRIPTIONDATE, contract.getSignedDate())
                        .set(CONTRACTS.BEGINDATE, contract.getBeginDate())
                        .set(CONTRACTS.ENDDATE, contract.getEndDate())
                        .set(CONTRACTS.SALARY, BigDecimal.valueOf(contract.getSalary()))
                        .where(CONTRACTS.CONTRACTID.eq(contract.getId()))
                        .execute())
                .closeConnection();
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

}
