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
    public @NonNull AccessType getAccessOfAccountEmail() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfAccountUsername() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfAccountPassword() {
        return AccessType.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfReviewID() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfReviewRating() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfReviewDate() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfReviewTime() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfReviewDescription() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfContractID() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfContractSubscriptionDate() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfContractBeginAndEndDate() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfContractSalary() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfEmployeeRole() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfPersonID() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfPersonName() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfPersonSurname() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfStaffNationalID() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfStaffDoB() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfStaffBirthPlace() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfStaffGender() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfExhibitionDate() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfExhibitionTime() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfExhibitionMaxSeats() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfExhibitionSpectatorNum() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfFacilityOpeningAndClosingTimes() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfMaintenancePrice() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfMaintenanceDescription() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfMaintenanceDate() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfParkServiceID() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfParkServiceName() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfParkServiceType() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfParkServiceDescription() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfPicturePath() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideIntensity() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideDuration() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideMaxSeats() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideHeightValues() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideWeightValues() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideStatus() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfRideWaitTime() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfShopRevenue() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfShopExpenses() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfShopMonth() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfShopYear() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfPriceListYear() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfSeasonTicketValidUntil() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfSeasonTicketDuration() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfSingleDayTicketValidOn() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketID() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketPurchaseDate() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketPunchDate() {
        return AccessType.ALL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketTypePrice() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketTypeType() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketTypeTarget() {
        return AccessType.READ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessType getAccessOfTicketTypeDuration() {
        return AccessType.READ;
    }

}
