package org.apdb4j.util;

import lombok.NonNull;
import java.time.LocalDateTime;

/**
 * Utility class for the generation of identifiers for some database tables.
 */
public final class IDGenerationUtils {

    private static final String CONTRACT_ID_PREFIX = "C-";
    private static final String EXHIBITION_ID_PREFIX = "EX-";
    private static final String RIDE_ID_PREFIX = "RI-";
    private static final String SHOP_ID_PREFIX = "SH-";
    private static final String TICKET_ID_PREFIX = "T-";
    private static final int ABBREVIATED_PERSON_NAME_LENGTH = 3;
    private static final int ABBREVIATED_PERSON_SURNAME_LENGTH = 3;

    private IDGenerationUtils() {
    }

    /**
     * Generates an identifier for a new contract.
     * @param employeeNID the identifier of the employee to be hired.
     * @param employerNID the identifier of the employer who is hiring the new employee.
     * @return the identifier for the contract to be added.
     */
    public static @NonNull String generateContractID(final @NonNull String employeeNID,
                                                     final @NonNull String employerNID) {
        return CONTRACT_ID_PREFIX + HashUtils.generate(employeeNID, employerNID, LocalDateTime.now());
    }

    /**
     * Generates an identifier for a new exhibition.
     * @return a new identifier for an exhibition.
     */
    public static @NonNull String generateExhibitionID() {
        return EXHIBITION_ID_PREFIX + HashUtils.generate(LocalDateTime.now());
    }

    /**
     * Generates an identifier for a new ride.
     * @return a new identifier for a ride.
     */
    public static @NonNull String generateRideID() {
        return RIDE_ID_PREFIX + HashUtils.generate(LocalDateTime.now());
    }

    /**
     * Generates an identifier for a new shop.
     * @return a new identifier for a shop.
     */
    public static @NonNull String generateShopID() {
        return SHOP_ID_PREFIX + HashUtils.generate(LocalDateTime.now());
    }

    /**
     * Generates the identifier for a new person (staff or guest).
     * @param personName the person's name
     * @param personSurname the person's surname
     * @param personEmail the person's email.
     * @return a new identifier for a person.
     */
    public static @NonNull String generatePersonID(final @NonNull String personName,
                                                   final @NonNull String personSurname,
                                                   final @NonNull String personEmail) {
        return abbreviate(personName, ABBREVIATED_PERSON_NAME_LENGTH)
                + "-" + abbreviate(personSurname, ABBREVIATED_PERSON_SURNAME_LENGTH)
                + "-" + HashUtils.generate(personEmail);
    }

    /**
     * Generates the identifier for a new review.
     * @return an identifier for a review.
     */
    public static @NonNull String generateReviewID() {
        return HashUtils.generate(LocalDateTime.now());
    }

    /**
     * Generates the identifier for a new ticket.
     * @return an identifier for a ticket.
     */
    public static @NonNull String generateTicketID() {
        return TICKET_ID_PREFIX + HashUtils.generate(LocalDateTime.now());
    }

    private static String abbreviate(final @NonNull String word, final int length) {
        return word.length() <= length ? word : word.substring(0, length);
    }
}
