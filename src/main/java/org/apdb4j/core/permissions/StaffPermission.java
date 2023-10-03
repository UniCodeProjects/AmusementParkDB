package org.apdb4j.core.permissions;

import lombok.NonNull;
import org.apdb4j.core.permissions.account.AccountAccess;
import org.apdb4j.core.permissions.account.ReviewAccess;
import org.apdb4j.core.permissions.people.ContractAccess;
import org.apdb4j.core.permissions.people.EmployeeAccess;
import org.apdb4j.core.permissions.services.ExhibitionAccess;
import org.apdb4j.core.permissions.services.MaintenanceAccess;
import org.apdb4j.core.permissions.services.PictureAccess;
import org.apdb4j.core.permissions.services.RideAccess;
import org.apdb4j.core.permissions.services.ShopAccess;
import org.apdb4j.core.permissions.tickets.PriceListAccess;
import org.apdb4j.core.permissions.tickets.SeasonTicketAccess;
import org.apdb4j.core.permissions.tickets.SingleDayTicketAccess;
import org.apdb4j.core.permissions.tickets.TicketTypeAccess;

/**
 * Specifies the permissions for a staff account.
 */
public class StaffPermission extends AbstractPermission implements AccountAccess, ReviewAccess, ContractAccess,
        EmployeeAccess, ExhibitionAccess, RideAccess, ShopAccess, MaintenanceAccess, PictureAccess, PriceListAccess,
        SeasonTicketAccess, SingleDayTicketAccess, TicketTypeAccess {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountEmail() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountUsername() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfAccountPassword() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
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
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewTime() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfReviewDescription() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfContractID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfContractSubscriptionDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfContractBeginAndEndDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfContractSalary() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfEmployeeRole() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfPersonID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfPersonName() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfPersonSurname() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfStaffNationalID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfStaffDoB() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfStaffBirthPlace() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfStaffGender() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionTime() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionMaxSeats() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfExhibitionSpectatorNum() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfFacilityOpeningAndClosingTimes() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfMaintenancePrice() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfMaintenanceDescription() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfMaintenanceDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
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
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceType() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfParkServiceDescription() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfPicturePath() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideIntensity() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideDuration() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideMaxSeats() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideHeightValues() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideWeightValues() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideStatus() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfRideWaitTime() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfShopRevenue() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfShopExpenses() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfShopMonth() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfShopYear() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfPriceListYear() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSeasonTicketValidUntil() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSeasonTicketDuration() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfSingleDayTicketValidOn() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketID() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketPurchaseDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketPunchDate() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypePrice() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeType() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeTarget() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSettings getAccessOfTicketTypeDuration() {
        return new ImmutableAccessSettings(AccessType.Read.NONE, AccessType.Write.NONE);
    }

}
