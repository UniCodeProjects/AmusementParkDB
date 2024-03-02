package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Collection;

/**
 * MVC controller that handles all the tickets bought by the currently logged user (if it is a guest).
 */
public interface BoughtTicketsController {

    /**
     * Retrieves some dates paired with the number of tickets that expire in that day.
     * Note that the type of the tickets can be only one, and it will be the type with which this instance has been
     * instantiated. For example, if the instance handles only {@link TicketType#SINGLE_DAY_TICKET}, this method will
     * retrieve the dates in which the single day tickets bought by the currently logged user are (or have been) valid on,
     * paired with the number of single day tickets that are valid on that specified date.
     * @see BoughtTicketsControllerImpl#BoughtTicketsControllerImpl(TicketType)
     * @return the dates in which the single day tickets or the season tickets bought by the user expire, paired with
     *         the number of tickets that expire in that day.
     */
    @NonNull Collection<Pair<LocalDate, Integer>> getNumberOfBoughtTickets();
}
