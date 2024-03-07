/*
 * This file is generated by jOOQ.
 */
package org.apdb4j.db;


import org.apdb4j.db.tables.Accounts;
import org.apdb4j.db.tables.Attributions;
import org.apdb4j.db.tables.Contracts;
import org.apdb4j.db.tables.Costs;
import org.apdb4j.db.tables.ExhibitionDetails;
import org.apdb4j.db.tables.Facilities;
import org.apdb4j.db.tables.Guests;
import org.apdb4j.db.tables.Maintenances;
import org.apdb4j.db.tables.MonthlyRecaps;
import org.apdb4j.db.tables.ParkServices;
import org.apdb4j.db.tables.Pictures;
import org.apdb4j.db.tables.PriceLists;
import org.apdb4j.db.tables.PunchDates;
import org.apdb4j.db.tables.Responsibilities;
import org.apdb4j.db.tables.Reviews;
import org.apdb4j.db.tables.RideDetails;
import org.apdb4j.db.tables.Rides;
import org.apdb4j.db.tables.Staff;
import org.apdb4j.db.tables.TicketTypes;
import org.apdb4j.db.tables.Tickets;
import org.apdb4j.db.tables.Validations;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * amusement_park.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<Record> KEY_ACCOUNTS_IDACCOUNT_1 = Internal.createUniqueKey(Accounts.ACCOUNTS, DSL.name("KEY_accounts_IDACCOUNT_1"), new TableField[] { Accounts.ACCOUNTS.USERNAME }, true);
    public static final UniqueKey<Record> KEY_ACCOUNTS_PRIMARY = Internal.createUniqueKey(Accounts.ACCOUNTS, DSL.name("KEY_accounts_PRIMARY"), new TableField[] { Accounts.ACCOUNTS.EMAIL }, true);
    public static final UniqueKey<Record> KEY_ATTRIBUTIONS_PRIMARY = Internal.createUniqueKey(Attributions.ATTRIBUTIONS, DSL.name("KEY_attributions_PRIMARY"), new TableField[] { Attributions.ATTRIBUTIONS.TICKETID }, true);
    public static final UniqueKey<Record> KEY_CONTRACTS_IDCONTRACT = Internal.createUniqueKey(Contracts.CONTRACTS, DSL.name("KEY_contracts_IDCONTRACT"), new TableField[] { Contracts.CONTRACTS.SUBSCRIPTIONDATE, Contracts.CONTRACTS.EMPLOYEENID }, true);
    public static final UniqueKey<Record> KEY_CONTRACTS_PRIMARY = Internal.createUniqueKey(Contracts.CONTRACTS, DSL.name("KEY_contracts_PRIMARY"), new TableField[] { Contracts.CONTRACTS.CONTRACTID }, true);
    public static final UniqueKey<Record> KEY_COSTS_PRIMARY = Internal.createUniqueKey(Costs.COSTS, DSL.name("KEY_costs_PRIMARY"), new TableField[] { Costs.COSTS.SHOPID, Costs.COSTS.MONTH, Costs.COSTS.YEAR }, true);
    public static final UniqueKey<Record> KEY_EXHIBITION_DETAILS_PRIMARY = Internal.createUniqueKey(ExhibitionDetails.EXHIBITION_DETAILS, DSL.name("KEY_exhibition_details_PRIMARY"), new TableField[] { ExhibitionDetails.EXHIBITION_DETAILS.EXHIBITIONID, ExhibitionDetails.EXHIBITION_DETAILS.DATE, ExhibitionDetails.EXHIBITION_DETAILS.TIME }, true);
    public static final UniqueKey<Record> KEY_FACILITIES_PRIMARY = Internal.createUniqueKey(Facilities.FACILITIES, DSL.name("KEY_facilities_PRIMARY"), new TableField[] { Facilities.FACILITIES.FACILITYID }, true);
    public static final UniqueKey<Record> KEY_GUESTS_FKR_1_ID = Internal.createUniqueKey(Guests.GUESTS, DSL.name("KEY_guests_FKR_1_ID"), new TableField[] { Guests.GUESTS.EMAIL }, true);
    public static final UniqueKey<Record> KEY_GUESTS_PRIMARY = Internal.createUniqueKey(Guests.GUESTS, DSL.name("KEY_guests_PRIMARY"), new TableField[] { Guests.GUESTS.GUESTID }, true);
    public static final UniqueKey<Record> KEY_MAINTENANCES_PRIMARY = Internal.createUniqueKey(Maintenances.MAINTENANCES, DSL.name("KEY_maintenances_PRIMARY"), new TableField[] { Maintenances.MAINTENANCES.FACILITYID, Maintenances.MAINTENANCES.DATE }, true);
    public static final UniqueKey<Record> KEY_MONTHLY_RECAPS_PRIMARY = Internal.createUniqueKey(MonthlyRecaps.MONTHLY_RECAPS, DSL.name("KEY_monthly_recaps_PRIMARY"), new TableField[] { MonthlyRecaps.MONTHLY_RECAPS.DATE }, true);
    public static final UniqueKey<Record> KEY_PARK_SERVICES_IDPARK_SERVICE_1 = Internal.createUniqueKey(ParkServices.PARK_SERVICES, DSL.name("KEY_park_services_IDPARK_SERVICE_1"), new TableField[] { ParkServices.PARK_SERVICES.NAME }, true);
    public static final UniqueKey<Record> KEY_PARK_SERVICES_PRIMARY = Internal.createUniqueKey(ParkServices.PARK_SERVICES, DSL.name("KEY_park_services_PRIMARY"), new TableField[] { ParkServices.PARK_SERVICES.PARKSERVICEID }, true);
    public static final UniqueKey<Record> KEY_PICTURES_PRIMARY = Internal.createUniqueKey(Pictures.PICTURES, DSL.name("KEY_pictures_PRIMARY"), new TableField[] { Pictures.PICTURES.PATH }, true);
    public static final UniqueKey<Record> KEY_PRICE_LISTS_PRIMARY = Internal.createUniqueKey(PriceLists.PRICE_LISTS, DSL.name("KEY_price_lists_PRIMARY"), new TableField[] { PriceLists.PRICE_LISTS.YEAR }, true);
    public static final UniqueKey<Record> KEY_PUNCH_DATES_PRIMARY = Internal.createUniqueKey(PunchDates.PUNCH_DATES, DSL.name("KEY_punch_dates_PRIMARY"), new TableField[] { PunchDates.PUNCH_DATES.DATE }, true);
    public static final UniqueKey<Record> KEY_RESPONSIBILITIES_PRIMARY = Internal.createUniqueKey(Responsibilities.RESPONSIBILITIES, DSL.name("KEY_responsibilities_PRIMARY"), new TableField[] { Responsibilities.RESPONSIBILITIES.EMPLOYEENID, Responsibilities.RESPONSIBILITIES.FACILITYID, Responsibilities.RESPONSIBILITIES.DATE }, true);
    public static final UniqueKey<Record> KEY_REVIEWS_PRIMARY = Internal.createUniqueKey(Reviews.REVIEWS, DSL.name("KEY_reviews_PRIMARY"), new TableField[] { Reviews.REVIEWS.REVIEWID }, true);
    public static final UniqueKey<Record> KEY_RIDE_DETAILS_PRIMARY = Internal.createUniqueKey(RideDetails.RIDE_DETAILS, DSL.name("KEY_ride_details_PRIMARY"), new TableField[] { RideDetails.RIDE_DETAILS.RIDEID }, true);
    public static final UniqueKey<Record> KEY_RIDES_PRIMARY = Internal.createUniqueKey(Rides.RIDES, DSL.name("KEY_rides_PRIMARY"), new TableField[] { Rides.RIDES.RIDEID }, true);
    public static final UniqueKey<Record> KEY_STAFF_FKR_ID = Internal.createUniqueKey(Staff.STAFF, DSL.name("KEY_staff_FKR_ID"), new TableField[] { Staff.STAFF.EMAIL }, true);
    public static final UniqueKey<Record> KEY_STAFF_IDSTAFF_1 = Internal.createUniqueKey(Staff.STAFF, DSL.name("KEY_staff_IDSTAFF_1"), new TableField[] { Staff.STAFF.STAFFID }, true);
    public static final UniqueKey<Record> KEY_STAFF_PRIMARY = Internal.createUniqueKey(Staff.STAFF, DSL.name("KEY_staff_PRIMARY"), new TableField[] { Staff.STAFF.NATIONALID }, true);
    public static final UniqueKey<Record> KEY_TICKET_TYPES_PRIMARY = Internal.createUniqueKey(TicketTypes.TICKET_TYPES, DSL.name("KEY_ticket_types_PRIMARY"), new TableField[] { TicketTypes.TICKET_TYPES.YEAR, TicketTypes.TICKET_TYPES.TYPE, TicketTypes.TICKET_TYPES.CATEGORY }, true);
    public static final UniqueKey<Record> KEY_TICKETS_PRIMARY = Internal.createUniqueKey(Tickets.TICKETS, DSL.name("KEY_tickets_PRIMARY"), new TableField[] { Tickets.TICKETS.TICKETID }, true);
    public static final UniqueKey<Record> KEY_VALIDATIONS_PRIMARY = Internal.createUniqueKey(Validations.VALIDATIONS, DSL.name("KEY_validations_PRIMARY"), new TableField[] { Validations.VALIDATIONS.DATE, Validations.VALIDATIONS.TICKETID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<Record, Record> FKATT_TIC_1 = Internal.createForeignKey(Attributions.ATTRIBUTIONS, DSL.name("FKatt_TIC_1"), new TableField[] { Attributions.ATTRIBUTIONS.YEAR, Attributions.ATTRIBUTIONS.TYPE, Attributions.ATTRIBUTIONS.CATEGORY }, Keys.KEY_TICKET_TYPES_PRIMARY, new TableField[] { TicketTypes.TICKET_TYPES.YEAR, TicketTypes.TICKET_TYPES.TYPE, TicketTypes.TICKET_TYPES.CATEGORY }, true);
    public static final ForeignKey<Record, Record> FKATT_TIC_FK = Internal.createForeignKey(Attributions.ATTRIBUTIONS, DSL.name("FKatt_TIC_FK"), new TableField[] { Attributions.ATTRIBUTIONS.TICKETID }, Keys.KEY_TICKETS_PRIMARY, new TableField[] { Tickets.TICKETS.TICKETID }, true);
    public static final ForeignKey<Record, Record> FKEMPLOYMENT = Internal.createForeignKey(Contracts.CONTRACTS, DSL.name("FKemployment"), new TableField[] { Contracts.CONTRACTS.EMPLOYEENID }, Keys.KEY_STAFF_PRIMARY, new TableField[] { Staff.STAFF.NATIONALID }, true);
    public static final ForeignKey<Record, Record> FKHIRING = Internal.createForeignKey(Contracts.CONTRACTS, DSL.name("FKhiring"), new TableField[] { Contracts.CONTRACTS.EMPLOYERNID }, Keys.KEY_STAFF_PRIMARY, new TableField[] { Staff.STAFF.NATIONALID }, true);
    public static final ForeignKey<Record, Record> FKCONNECTION = Internal.createForeignKey(Costs.COSTS, DSL.name("FKconnection"), new TableField[] { Costs.COSTS.SHOPID }, Keys.KEY_FACILITIES_PRIMARY, new TableField[] { Facilities.FACILITIES.FACILITYID }, true);
    public static final ForeignKey<Record, Record> FKR = Internal.createForeignKey(ExhibitionDetails.EXHIBITION_DETAILS, DSL.name("FKR"), new TableField[] { ExhibitionDetails.EXHIBITION_DETAILS.EXHIBITIONID }, Keys.KEY_PARK_SERVICES_PRIMARY, new TableField[] { ParkServices.PARK_SERVICES.PARKSERVICEID }, true);
    public static final ForeignKey<Record, Record> FKR_FK = Internal.createForeignKey(Facilities.FACILITIES, DSL.name("FKR_FK"), new TableField[] { Facilities.FACILITIES.FACILITYID }, Keys.KEY_PARK_SERVICES_PRIMARY, new TableField[] { ParkServices.PARK_SERVICES.PARKSERVICEID }, true);
    public static final ForeignKey<Record, Record> FKR_1_FK = Internal.createForeignKey(Guests.GUESTS, DSL.name("FKR_1_FK"), new TableField[] { Guests.GUESTS.EMAIL }, Keys.KEY_ACCOUNTS_PRIMARY, new TableField[] { Accounts.ACCOUNTS.EMAIL }, true);
    public static final ForeignKey<Record, Record> FKEXECUTION = Internal.createForeignKey(Maintenances.MAINTENANCES, DSL.name("FKexecution"), new TableField[] { Maintenances.MAINTENANCES.FACILITYID }, Keys.KEY_FACILITIES_PRIMARY, new TableField[] { Facilities.FACILITIES.FACILITYID }, true);
    public static final ForeignKey<Record, Record> FKREPRESENT = Internal.createForeignKey(Pictures.PICTURES, DSL.name("FKrepresent"), new TableField[] { Pictures.PICTURES.PARKSERVICEID }, Keys.KEY_PARK_SERVICES_PRIMARY, new TableField[] { ParkServices.PARK_SERVICES.PARKSERVICEID }, true);
    public static final ForeignKey<Record, Record> FKRES_MAI = Internal.createForeignKey(Responsibilities.RESPONSIBILITIES, DSL.name("FKres_MAI"), new TableField[] { Responsibilities.RESPONSIBILITIES.FACILITYID, Responsibilities.RESPONSIBILITIES.DATE }, Keys.KEY_MAINTENANCES_PRIMARY, new TableField[] { Maintenances.MAINTENANCES.FACILITYID, Maintenances.MAINTENANCES.DATE }, true);
    public static final ForeignKey<Record, Record> FKRES_STA = Internal.createForeignKey(Responsibilities.RESPONSIBILITIES, DSL.name("FKres_STA"), new TableField[] { Responsibilities.RESPONSIBILITIES.EMPLOYEENID }, Keys.KEY_STAFF_PRIMARY, new TableField[] { Staff.STAFF.NATIONALID }, true);
    public static final ForeignKey<Record, Record> FKPUBLICATION = Internal.createForeignKey(Reviews.REVIEWS, DSL.name("FKpublication"), new TableField[] { Reviews.REVIEWS.ACCOUNT }, Keys.KEY_ACCOUNTS_PRIMARY, new TableField[] { Accounts.ACCOUNTS.EMAIL }, true);
    public static final ForeignKey<Record, Record> FKREFERENCE = Internal.createForeignKey(Reviews.REVIEWS, DSL.name("FKreference"), new TableField[] { Reviews.REVIEWS.PARKSERVICEID }, Keys.KEY_PARK_SERVICES_PRIMARY, new TableField[] { ParkServices.PARK_SERVICES.PARKSERVICEID }, true);
    public static final ForeignKey<Record, Record> FKRIDE_RIDE_DETAIL_FK = Internal.createForeignKey(RideDetails.RIDE_DETAILS, DSL.name("FKride_ride_detail_FK"), new TableField[] { RideDetails.RIDE_DETAILS.RIDEID }, Keys.KEY_RIDES_PRIMARY, new TableField[] { Rides.RIDES.RIDEID }, true);
    public static final ForeignKey<Record, Record> FKR_FKR = Internal.createForeignKey(Rides.RIDES, DSL.name("FKR_FKR"), new TableField[] { Rides.RIDES.RIDEID }, Keys.KEY_FACILITIES_PRIMARY, new TableField[] { Facilities.FACILITIES.FACILITYID }, true);
    public static final ForeignKey<Record, Record> FKR_FKS = Internal.createForeignKey(Staff.STAFF, DSL.name("FKR_FKS"), new TableField[] { Staff.STAFF.EMAIL }, Keys.KEY_ACCOUNTS_PRIMARY, new TableField[] { Accounts.ACCOUNTS.EMAIL }, true);
    public static final ForeignKey<Record, Record> FKCOMPOSITION = Internal.createForeignKey(TicketTypes.TICKET_TYPES, DSL.name("FKcomposition"), new TableField[] { TicketTypes.TICKET_TYPES.YEAR }, Keys.KEY_PRICE_LISTS_PRIMARY, new TableField[] { PriceLists.PRICE_LISTS.YEAR }, true);
    public static final ForeignKey<Record, Record> FKPURCHASE = Internal.createForeignKey(Tickets.TICKETS, DSL.name("FKpurchase"), new TableField[] { Tickets.TICKETS.OWNERID }, Keys.KEY_GUESTS_PRIMARY, new TableField[] { Guests.GUESTS.GUESTID }, true);
    public static final ForeignKey<Record, Record> FKVAL_PUN = Internal.createForeignKey(Validations.VALIDATIONS, DSL.name("FKval_PUN"), new TableField[] { Validations.VALIDATIONS.DATE }, Keys.KEY_PUNCH_DATES_PRIMARY, new TableField[] { PunchDates.PUNCH_DATES.DATE }, true);
    public static final ForeignKey<Record, Record> FKVAL_TIC = Internal.createForeignKey(Validations.VALIDATIONS, DSL.name("FKval_TIC"), new TableField[] { Validations.VALIDATIONS.TICKETID }, Keys.KEY_TICKETS_PRIMARY, new TableField[] { Tickets.TICKETS.TICKETID }, true);
}
