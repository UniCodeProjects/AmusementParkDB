package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apdb4j.core.permissions.account.AccountAccess;
import org.apdb4j.core.permissions.account.ReviewAccess;
import org.apdb4j.core.permissions.services.ExhibitionAccess;
import org.apdb4j.core.permissions.services.RideAccess;
import org.apdb4j.core.permissions.tickets.SeasonTicketAccess;
import org.apdb4j.core.permissions.tickets.SingleDayTicketAccess;
import org.apdb4j.core.permissions.tickets.TicketTypeAccess;

import java.util.Set;

/**
 * Specifies the permissions for a guest account.
 */
public class GuestPermission extends AbstractPermission implements AccountAccess, ReviewAccess, ExhibitionAccess, RideAccess,
        SeasonTicketAccess, SingleDayTicketAccess, TicketTypeAccess {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountEmail() {
        return new ImmutableAccessSettings(
                AccessType.Read.LOCAL,
                Pair.of(AccessType.Write.LOCAL, Set.of(GuestPermission.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountUsername() {
        return new ImmutableAccessSettings(
                AccessType.Read.GLOBAL,
                Pair.of(AccessType.Write.LOCAL, Set.of(GuestPermission.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountPassword() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewRating() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewDate() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewTime() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewDescription() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionDate() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionTime() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionMaxSeats() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionSpectatorNum() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfFacilityOpeningAndClosingTimes() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceName() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceType() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceDescription() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideIntensity() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideDuration() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideMaxSeats() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideHeightValues() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideWeightValues() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideStatus() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideWaitTime() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSeasonTicketValidUntil() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSeasonTicketDuration() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSingleDayTicketValidOn() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.LOCAL);    // todo: only for the first time?
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketID() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketPurchaseDate() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketPunchDate() {
        return new ImmutableAccessSettings(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypePrice() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeType() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeTarget() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeDuration() {
        return new ImmutableAccessSettings(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

}
