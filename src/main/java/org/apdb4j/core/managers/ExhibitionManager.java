package org.apdb4j.core.managers;

import lombok.NonNull;
import org.apdb4j.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * Contains all the SQL queries that are related to the entity EXHIBITION.
 */
public interface ExhibitionManager {

    /**
     * Performs the SQL query that adds a new exhibition without a description, that will be set to {@code null}.
     * @param exhibitionID the identifier of the new exhibition.
     * @param name the name of the new exhibition.
     * @param type the type of the new exhibition.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void addNewExhibition(@NonNull String exhibitionID,
                          @NonNull String name,
                          @NonNull String type,
                          @NonNull String account);

    /**
     * Performs the SQL query that adds a new exhibition.
     * @param exhibitionID the identifier of the new exhibition.
     * @param name the name of the new exhibition.
     * @param type the type of the new exhibition.
     * @param description the description of the new exhibition. If it is {@code null} the behavior of this method
     *                    is the same of
     *                    {@link ExhibitionManager#addNewExhibition(String, String, String, String)}.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void addNewExhibitionWithDescription(@NonNull String exhibitionID,
                                         @NonNull String name,
                                         @NonNull String type,
                                         @NonNull String description,
                                         @NonNull String account);

    /**
     * Performs the SQL query that plans a new exhibition, so inserts a new tuple with the
     * provided values in the {@link org.apdb4j.db.tables.ExhibitionDetails} table.
     * @param exhibitionID the ID of the planned exhibition. If the value of this parameter is not the identifier
     *                     of an exhibition, the query will not be executed.
     * @param date the date on which the exhibition will be performed.
     * @param time the hour on which the exhibition will be performed.
     * @param maxSeats the maximum number of seats of the exhibition venue.
     * @param account the account that is performing this operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void planNewExhibition(@NonNull String exhibitionID,
                           @NonNull LocalDate date, @NonNull LocalTime time,
                           int maxSeats,
                           @NonNull String account);

    /**
     * Performs the SQL query that changes the maximum number of seats for the provided exhibition.
     * @param exhibitionID the ID of the exhibition. If the value of this parameter is not the identifier of an
     *                     exhibition, the query will not be executed.
     * @param date the date on which the exhibition will be performed. The date needs to be in the future, otherwise
     *             the query will not be executed.
     * @param time the time on which the exhibition will be performed.
     *             The time needs to be in the future if {@code date} is the current day. Otherwise, the query will not
     *             be executed.
     * @param newMaxSeats the new maximum number of seats.
     * @param account the account that is performing this operation. If the account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void changeMaxSeats(@NonNull String exhibitionID,
                        @NonNull LocalDate date, @NonNull LocalTime time,
                        int newMaxSeats,
                        @NonNull String account);

    /**
     * Performs the SQL query that adds the number of spectators for an ended exhibition.
     * @param exhibitionID the ID of the exhibition. If the value of this parameter is not the identifier
     *                     of an exhibition, the query will not be executed.
     * @param date the date on which the exhibition was performed. The date has to be in the past, or at least
     *             the current date. Otherwise, the query will not be executed.
     * @param time the hour on which the exhibition was performed. The time has to be in the past if {@code date} is the
     *             current date, otherwise the query will not be executed.
     * @param spectators the number of people that watched the exhibition.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     */
    void addSpectatorsNum(@NonNull String exhibitionID,
                          @NonNull LocalDate date, @NonNull LocalTime time,
                          int spectators,
                          @NonNull String account);

    /**
     * Performs the SQL query that computes the average number of spectators for each
     * exhibition type.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return a collection in which each exhibition type is paired with its average number of spectators.
     */
    @NonNull Collection<Pair<String, Integer>> getAverageSpectatorsForType(@NonNull String account);

    /**
     * Performs the SQL query that computes the percentage of exhibitions that were sold-out until now.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return the percentage of sold-out exhibitions.
     */
    double computePercentageOfSoldOutExhibitions(@NonNull String account);

    /**
     * Realises the SQL query that retrieves all the exhibitions that are going to be performed in the future.
     * @param account the account that is performing the operation. If this account has not the permissions
     *                to accomplish the operation, the query will not be executed.
     * @return all the exhibitions that are planned for the future.
     */
    @NonNull Collection<Record> viewAllPlannedExhibitions(@NonNull String account);

}