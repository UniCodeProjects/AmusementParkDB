package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apdb4j.core.managers.PictureManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.PictureTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.PICTURES;

/**
 * An implementation of a picture controller.
 */
public class PictureControllerImpl implements PictureController {

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractPictureData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final PictureTableItem picture = (PictureTableItem) item;
        final boolean queryResult = PictureManager.addPicture(picture.getPath(), picture.getServiceID(), "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while adding a new picture.");
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException use method {@link PictureController#editPicture(String, PictureTableItem)} instead
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends PictureTableItem> T editPicture(final String oldPath, final T newPicture) {
        final boolean queryResult = PictureManager.editPicture(oldPath, newPicture.getPath(), "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while editing the picture.");
        }
        return newPicture;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends PictureTableItem> T removePicture(final T picture) {
        // fixme
        final boolean queryResult = PictureManager.removePicture(picture.getPath(), "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while removing the picture.");
        }
        return picture;
    }

    /**
     * {@inheritDoc}
     * @throws org.jooq.exception.DataAccessException if the query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String parkServiceID) {
        return extractPictureData(searchQuery(PICTURES.PARKSERVICEID.containsIgnoreCase(parkServiceID)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<String> getErrorMessage() {
        return Optional.empty();
    }

    private Result<Record> searchQuery(final Condition condition) {
        return new QueryBuilder().createConnection()
                .queryAction(db -> db.select()
                        .from(PICTURES)
                        .where(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractPictureData(final Result<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new PictureTableItem(record.get(PICTURES.PARKSERVICEID),
                record.get(PICTURES.PATH))));
        return data;
    }

}
