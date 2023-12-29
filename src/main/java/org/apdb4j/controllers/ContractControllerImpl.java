package org.apdb4j.controllers;

import lombok.NonNull;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.ContractTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.CONTRACTS;

/**
 * An administration controller specifically used for contracts.
 */
public class ContractControllerImpl implements AdministrationController {

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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

}
