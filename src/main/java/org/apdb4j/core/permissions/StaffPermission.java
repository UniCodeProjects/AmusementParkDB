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

import java.util.Set;

import static org.apdb4j.db.Tables.ACCOUNTS;
import static org.apdb4j.db.Tables.CONTRACTS;
import static org.apdb4j.db.Tables.COSTS;
import static org.apdb4j.db.Tables.EXHIBITION_DETAILS;
import static org.apdb4j.db.Tables.FACILITIES;
import static org.apdb4j.db.Tables.MAINTENANCES;
import static org.apdb4j.db.Tables.PARK_SERVICES;
import static org.apdb4j.db.Tables.PICTURES;
import static org.apdb4j.db.Tables.PRICE_LISTS;
import static org.apdb4j.db.Tables.PUNCH_DATES;
import static org.apdb4j.db.Tables.REVIEWS;
import static org.apdb4j.db.Tables.RIDES;
import static org.apdb4j.db.Tables.RIDE_DETAILS;
import static org.apdb4j.db.Tables.STAFF;
import static org.apdb4j.db.Tables.TICKETS;
import static org.apdb4j.db.Tables.TICKET_TYPES;

/**
 * Specifies the permissions for a staff account.
 */
public class StaffPermission implements AccountAccess, ReviewAccess, ContractAccess,
        EmployeeAccess, ExhibitionAccess, RideAccess, ShopAccess, MaintenanceAccess, PictureAccess, PriceListAccess,
        SeasonTicketAccess, SingleDayTicketAccess, TicketTypeAccess {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountEmail() {
        return AccessSetting.of(ACCOUNTS.EMAIL, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountUsername() {
        return AccessSetting.of(ACCOUNTS.USERNAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfAccountPassword() {
        return AccessSetting.of(ACCOUNTS.PASSWORD, AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewID() {
        return AccessSetting.of(REVIEWS.REVIEWID, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewRating() {
        return AccessSetting.of(REVIEWS.RATING, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewDate() {
        return AccessSetting.of(REVIEWS.DATE, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewTime() {
        return AccessSetting.of(REVIEWS.TIME, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfReviewDescription() {
        return AccessSetting.of(REVIEWS.DESCRIPTION, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractID() {
        return AccessSetting.of(CONTRACTS.CONTRACTID, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractSubscriptionDate() {
        return AccessSetting.of(CONTRACTS.SUBSCRIPTIONDATE, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractBeginAndEndDate() {
        return AccessSetting.of(Set.of(CONTRACTS.BEGINDATE, CONTRACTS.ENDDATE), AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfContractSalary() {
        return AccessSetting.of(CONTRACTS.SALARY, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfEmployeeRole() {
        return AccessSetting.of(STAFF.ROLE, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonID() {
        return AccessSetting.of(STAFF.STAFFID, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonName() {
        return AccessSetting.of(STAFF.NAME, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPersonSurname() {
        return AccessSetting.of(STAFF.SURNAME, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffNationalID() {
        return AccessSetting.of(STAFF.NATIONALID, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffDoB() {
        return AccessSetting.of(STAFF.DOB, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffBirthPlace() {
        return AccessSetting.of(STAFF.BIRTHPLACE, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfStaffGender() {
        return AccessSetting.of(STAFF.GENDER, AccessType.Read.LOCAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionDate() {
        return AccessSetting.of(EXHIBITION_DETAILS.DATE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionTime() {
        return AccessSetting.of(EXHIBITION_DETAILS.TIME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionMaxSeats() {
        return AccessSetting.of(EXHIBITION_DETAILS.MAXSEATS, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfExhibitionSpectatorNum() {
        return AccessSetting.of(EXHIBITION_DETAILS.SPECTATORS, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfFacilityOpeningAndClosingTimes() {
        return AccessSetting.of(Set.of(FACILITIES.OPENINGTIME, FACILITIES.CLOSINGTIME),
                AccessType.Read.GLOBAL,
                AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfMaintenancePrice() {
        return AccessSetting.of(MAINTENANCES.PRICE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfMaintenanceDescription() {
        return AccessSetting.of(MAINTENANCES.DESCRIPTION, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfMaintenanceDate() {
        return AccessSetting.of(MAINTENANCES.DATE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceID() {
        return AccessSetting.of(PARK_SERVICES.PARKSERVICEID, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceName() {
        return AccessSetting.of(PARK_SERVICES.NAME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceType() {
        return AccessSetting.of(PARK_SERVICES.TYPE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfParkServiceDescription() {
        return AccessSetting.of(PARK_SERVICES.DESCRIPTION, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPicturePath() {
        return AccessSetting.of(PICTURES.PATH, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideIntensity() {
        return AccessSetting.of(RIDES.INTENSITY, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideDuration() {
        return AccessSetting.of(RIDES.DURATION, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideMaxSeats() {
        return AccessSetting.of(RIDES.MAXSEATS, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideHeightValues() {
        return AccessSetting.of(Set.of(RIDES.MINHEIGHT, RIDES.MAXHEIGHT), AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWeightValues() {
        return AccessSetting.of(Set.of(RIDES.MINWEIGHT, RIDES.MAXWEIGHT), AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideStatus() {
        return AccessSetting.of(RIDE_DETAILS.STATUS, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfRideWaitTime() {
        return AccessSetting.of(RIDE_DETAILS.ESTIMATEDWAITTIME, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopRevenue() {
        return AccessSetting.of(COSTS.REVENUE, AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopExpenses() {
        return AccessSetting.of(COSTS.EXPENSES, AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopMonth() {
        return AccessSetting.of(COSTS.MONTH, AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfShopYear() {
        return AccessSetting.of(COSTS.YEAR, AccessType.Read.LOCAL, AccessType.Write.LOCAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfPriceListYear() {
        return AccessSetting.of(PRICE_LISTS.YEAR, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSeasonTicketValidUntil() {
        return AccessSetting.of(TICKETS.VALIDUNTIL, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfSingleDayTicketValidOn() {
        return AccessSetting.of(TICKETS.VALIDON, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketID() {
        return AccessSetting.of(TICKETS.TICKETID, AccessType.Read.GLOBAL, AccessType.Write.NONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPurchaseDate() {
        return AccessSetting.of(TICKETS.PURCHASEDATE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketPunchDate() {
        return AccessSetting.of(PUNCH_DATES.DATE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketRemainingEntrances() {
        return AccessSetting.of(TICKETS.REMAININGENTRANCES, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypePrice() {
        return AccessSetting.of(TICKET_TYPES.PRICE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeType() {
        return AccessSetting.of(TICKET_TYPES.TYPE, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeTarget() {
        return AccessSetting.of(TICKET_TYPES.CATEGORY, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull AccessSetting getAccessOfTicketTypeDuration() {
        return AccessSetting.of(TICKET_TYPES.DURATION, AccessType.Read.GLOBAL, AccessType.Write.GLOBAL);
    }

}
