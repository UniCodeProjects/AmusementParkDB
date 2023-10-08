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
    public @NonNull AccessSetting getAccessOfAccountEmail() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountUsername() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
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
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewRating() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
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
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractID() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractSubscriptionDate() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractBeginAndEndDate() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractSalary() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfEmployeeRole() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonID() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonName() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonSurname() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffNationalID() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffDoB() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffBirthPlace() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffGender() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionTime() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionMaxSeats() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionSpectatorNum() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
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
    public @NonNull AccessSetting getAccessOfMaintenancePrice() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfMaintenanceDescription() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfMaintenanceDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceID() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceName() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceType() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceDescription() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPicturePath() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideIntensity() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideDuration() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideMaxSeats() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideHeightValues() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWeightValues() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideStatus() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWaitTime() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopRevenue() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopExpenses() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopMonth() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopYear() {
        return new ImmutableAccessSetting(AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPriceListYear() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSeasonTicketValidUntil() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSingleDayTicketValidOn() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketID() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPurchaseDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPunchDate() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketRemainingEntrances() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypePrice() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeType() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeTarget() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeDuration() {
        return new ImmutableAccessSetting(AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

}
