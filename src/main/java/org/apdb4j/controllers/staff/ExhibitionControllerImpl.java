package org.apdb4j.controllers.staff;

import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.managers.ExhibitionManager;
import org.apdb4j.util.QueryBuilder;
import org.apdb4j.view.staff.tableview.ExhibitionTableItem;
import org.apdb4j.view.staff.tableview.TableItem;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apdb4j.db.Tables.EXHIBITION_DETAILS;
import static org.apdb4j.db.Tables.PARK_SERVICES;

/**
 * An implementation of an exhibition controller.
 */
public class ExhibitionControllerImpl implements ExhibitionController {

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> getData() {
        return extractExhibitionData(searchQuery(DSL.condition(true)));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T addData(final T item) {
        final ExhibitionTableItem exhibition = (ExhibitionTableItem) item;
        final boolean queryResult = ExhibitionManager.addNewExhibitionWithDescription(exhibition.getId(),
                exhibition.getName(),
                exhibition.getType(),
                exhibition.getDescription(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while adding a new exhibition.");
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> T editData(final T item) {
        final ExhibitionTableItem exhibition = (ExhibitionTableItem) item;
        // FIXME: duplicate key (NAME).
        if (exhibition.getDate() == null || exhibition.getTime() == null) {
            new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(PARK_SERVICES
                                    .join(EXHIBITION_DETAILS)
                                    .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID)))
                            .set(PARK_SERVICES.NAME, exhibition.getName())
                            .set(PARK_SERVICES.TYPE, exhibition.getType())
                            .set(PARK_SERVICES.DESCRIPTION, exhibition.getDescription())
                            .execute())
                    .closeConnection();
        } else {
            new QueryBuilder().createConnection()
                    .queryAction(db -> db.update(PARK_SERVICES
                                    .join(EXHIBITION_DETAILS)
                                    .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID)))
                            .set(PARK_SERVICES.NAME, exhibition.getName())
                            .set(PARK_SERVICES.TYPE, exhibition.getType())
                            .set(PARK_SERVICES.DESCRIPTION, exhibition.getDescription())
                            .set(EXHIBITION_DETAILS.DATE, exhibition.getDate())
                            .set(EXHIBITION_DETAILS.TIME, exhibition.getTime())
                            .execute())
                    .closeConnection();
        }
        return item;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends TableItem> Collection<T> filter(final String exhibitionName) {
        return extractExhibitionData(searchQuery(PARK_SERVICES.NAME.containsIgnoreCase(exhibitionName)));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends ExhibitionTableItem> T updateSpectatorsNumber(final T exhibition) {
        final boolean queryResult = ExhibitionManager.addSpectatorsNum(exhibition.getId(),
                exhibition.getDate(),
                exhibition.getTime(),
                exhibition.getSpectators(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while updating the spectators number");
        }
        return exhibition;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends ExhibitionTableItem> T updateMaxSeats(final T exhibition) {
        final boolean queryResult = ExhibitionManager.changeMaxSeats(exhibition.getId(),
                exhibition.getDate(),
                exhibition.getTime(),
                exhibition.getMaxSeats(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while updating the maximum seats number");
        }
        return exhibition;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends ExhibitionTableItem> T planExhibition(final T exhibition) {
        final boolean queryResult = ExhibitionManager.planNewExhibition(exhibition.getId(),
                exhibition.getDate(),
                exhibition.getTime(),
                exhibition.getMaxSeats(),
                "");
        if (!queryResult) {
            throw new DataAccessException("Something went wrong while planning and exhibition.");
        }
        return exhibition;
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public Collection<Pair<String, Integer>> getAverageSpectatorsByType() {
        return ExhibitionManager.getAverageSpectatorsForType("");
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public double getSoldOutExhibitionPercentage() {
        return ExhibitionManager.computePercentageOfSoldOutExhibitions("");
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public <T extends ExhibitionTableItem> Collection<T> viewPlannedExhibitions() {
        return extractExhibitionData(ExhibitionManager.viewAllPlannedExhibitions(""));
    }

    /**
     * {@inheritDoc}
     * @throws DataAccessException if query fails
     */
    @Override
    public List<String> getExistingTypes() {
        return Arrays.stream(new QueryBuilder().createConnection()
                        .queryAction(db -> db.selectDistinct(PARK_SERVICES.TYPE)
                                .from(PARK_SERVICES)
                                .fetch())
                        .closeConnection()
                        .getResultAsRecords()
                        .sortAsc(PARK_SERVICES.TYPE)
                        .intoArray(PARK_SERVICES.TYPE))
                .toList();
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
                        .from(PARK_SERVICES)
                        .leftJoin(EXHIBITION_DETAILS)
                        .on(PARK_SERVICES.PARKSERVICEID.eq(EXHIBITION_DETAILS.EXHIBITIONID))
                        .where(PARK_SERVICES.ISEXHIBITION.isTrue())
                        .and(condition)
                        .fetch())
                .closeConnection()
                .getResultAsRecords();
    }

    @SuppressWarnings("unchecked")
    private <T extends TableItem> Collection<T> extractExhibitionData(final Collection<Record> result) {
        final List<T> data = new ArrayList<>();
        result.forEach(record -> data.add((T) new ExhibitionTableItem(record.get(PARK_SERVICES.PARKSERVICEID),
                record.get(PARK_SERVICES.NAME),
                record.get(PARK_SERVICES.TYPE),
                record.get(PARK_SERVICES.DESCRIPTION),
                record.get(EXHIBITION_DETAILS.DATE),
                record.get(EXHIBITION_DETAILS.TIME),
                ObjectUtils.defaultIfNull(record.get(EXHIBITION_DETAILS.MAXSEATS), 0),
                ObjectUtils.defaultIfNull(record.get(EXHIBITION_DETAILS.SPECTATORS), 0).intValue(),
                ObjectUtils.defaultIfNull(record.get(PARK_SERVICES.AVGRATING), 0.0).doubleValue(),
                ObjectUtils.defaultIfNull(record.get(PARK_SERVICES.NUMREVIEWS), 0).intValue())));
        return data;
    }

}
