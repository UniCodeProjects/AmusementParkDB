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
    public @NonNull AccessSetting getAccessOfAccountEmail() {
        return new ImmutableAccessSetting(
                AccessType.Read.LOCAL,
                Pair.of(AccessType.Write.LOCAL, Set.of(GuestPermission.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountUsername() {
        return new ImmutableAccessSetting(
                AccessType.Read.GLOBAL,
                Pair.of(AccessType.Write.LOCAL, Set.of(GuestPermission.class)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountPassword() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewID() {
        return new ImmutableAccessSetting(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewRating() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewTime() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewDescription() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionTime() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionMaxSeats() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionSpectatorNum() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfFacilityOpeningAndClosingTimes() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceID() {
        return new ImmutableAccessSetting(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceName() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceType() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceDescription() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideIntensity() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideDuration() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideMaxSeats() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideHeightValues() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWeightValues() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideStatus() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWaitTime() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSeasonTicketValidUntil() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSeasonTicketDuration() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSingleDayTicketValidOn() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);    // todo: only for the first time?
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketID() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPurchaseDate() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPunchDate() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypePrice() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeType() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeTarget() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeDuration() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

}
