create database Amusement_Park;
use Amusement_Park;

-- Tables Section
-- _____________ 

create table ACCOUNTS (
     Email varchar(256) not null,
     Username varchar(30),
     Password varchar(30),
     PermissionType varchar(30) not null,
     constraint IDACCOUNT primary key (Email),
     constraint IDACCOUNT_1 unique (Username),
     constraint PSW_LENGTH check (length(Password) >= 8));

create table attributions (
     TicketID char(65) not null,
     Year int not null,
     Type varchar(50) not null,
     Category varchar(50) not null,
     constraint FKatt_TIC_ID primary key (TicketID));

create table CONTRACTS (
     ContractID char(65) not null,
     SubscriptionDate date not null,
     BeginDate date not null,
     EndDate date,
     Salary decimal(7,2) not null,
     EmployerNID varchar(256) not null,
     EmployeeNID varchar(256) not null,
     constraint IDCONTRATTO primary key (ContractID),
     constraint IDCONTRACT unique (SubscriptionDate, EmployeeNID),
     constraint CONTRACTID_FORMAT check (ContractID like 'C%'),
     constraint BEGINDATE_FORMAT check (day(BeginDate) = 1),
     constraint ENDDATE_FORMAT check (EndDate is null or EndDate = last_day(EndDate)),
     constraint DATES_CONSISTENCY_1 check (SubscriptionDate <= BeginDate),
     constraint DATES_CONSISTENCY_2 check (EndDate is null or (BeginDate < EndDate)),
     constraint SALARY_NON_NEGATIVITY_CHECK check (Salary > 0));

create table COSTS (
     ShopID char(6) not null,
     Revenue decimal(8,2) not null,
     Expenses decimal(8,2) not null,
     Month int not null,
     Year int not null,
     constraint COST_ID_CHECK check ((ShopID like 'SH%') or (ShopID like 'RE%')),
     constraint MONEY_DATA_NON_NEGATIVITY_CHECK check (Revenue >= 0 and Expenses >= 0),
     constraint MONTH_DOMAIN check (Month between 1 and 12),
     constraint IDCOST primary key (ShopID, Month, Year));

create table EXHIBITION_DETAILS (
     ExhibitionID char(6) not null,
     Date date not null,
     Time time not null,
     MaxSeats int not null,
     Spectators int unsigned,
     constraint IDEXHIBITION_DETAIL primary key (ExhibitionID, Date, Time),
     constraint EX_DET_ID_CHECK check (ExhibitionID like 'EX%'),
     constraint SPECTATORS_CONSISTENCY check (Spectators is null or (Spectators <= MaxSeats)),
     constraint EX_DET_MAX_SEATS_DOMAIN check (MaxSeats > 0));

create table FACILITIES (
     FacilityID char(6) not null,
     OpeningTime time not null,
     ClosingTime time not null,
     IsShop boolean not null,
     constraint FKR_ID primary key (FacilityID),
     constraint FACILITYID_CHECK check (FacilityID like 'SH%' or FacilityID like 'RE%' or FacilityID like 'RI%'),
     -- if IsShop is true, then the facility must be a shop. If IsShop is false, then the facility must not be a shop.
     constraint SHOP_CHECK check ((IsShop = false or (FacilityID like 'SH%' or FacilityID like 'RE%')) and (IsShop = true or (FacilityID like 'RI%'))),
     constraint TIMES_CONSISTENCY check (OpeningTime < ClosingTime));

create table GUESTS (
     GuestID char(72) not null,
     Email varchar(256) not null,
     Name varchar(256) not null,
     Surname varchar(256) not null,
     constraint IDGUEST primary key (GuestID),
     constraint FKR_1_ID unique (Email));

create table MAINTENANCES (
     FacilityID char(6) not null,
     Price decimal(8,2) not null,
     Description varchar(4000) not null,
     Date date not null,
     constraint IDMAINTENANCE_ID primary key (FacilityID, Date),
     constraint PRICE_NOT_NEGATIVITY_CHECK check (Price >= 0));

create table MONTHLY_RECAPS (
     Date date not null,
     Revenue decimal(9,2) not null,
     constraint IDDAILY_RECAP primary key (Date),
     constraint DAY_FORMAT check (day(Date) = 1), -- each date must be the first day of the month
     constraint REVENUE_NON_NEGATIVITY_CHECK check (Revenue >= 0));

create table PARK_SERVICES (
     ParkServiceID char(6) not null,
     Name varchar(256) not null,
     AvgRating decimal(2,1) not null,
     NumReviews int unsigned not null,
     Type varchar(256) not null,
     Description varchar(1000),
     IsExhibition boolean not null,
     constraint IDPARK_SERVICE primary key (ParkServiceID),
     constraint IDPARK_SERVICE_1 unique (Name),
     constraint PARKSERVICEID_CHECK check (ParkServiceID like 'SH%' or ParkServiceID like 'RE%' or ParkServiceID like 'RI%' or ParkServiceID like 'EX%'),
     -- if IsExhibition is true, then the park service must be an exhibition. If IsExhibition is false, then the park service must not be an exhibition.
     constraint EXHIBITION_CHECK check ((IsExhibition = false or (ParkServiceID like 'EX%')) and (IsExhibition = true or (ParkServiceID like 'SH%' or ParkServiceID like 'RE%' or ParkServiceID like 'RI%'))),
     constraint AVGRATING_DOMAIN check (AvgRating between 0 and 5),
     constraint AVGRATING_CHECK check ((AvgRating = 0.0 and NumReviews = 0) or (AvgRating >= 1.0 and NumReviews >= 1)));

create table PERMISSIONS (
     PermissionType varchar(30) not null,
     AccessSequence varchar(1000) not null,
     constraint IDACCESS primary key (PermissionType));

create table PICTURES (
     Path varchar(256) not null,
     ParkServiceID char(6) not null,
     constraint IDPICTURE primary key (Path));

create table PRICE_LISTS (
     Year int not null,
     constraint IDTIME primary key (Year));

create table PUNCH_DATES (
     Date date not null,
     constraint IDPUNCH_DATES primary key (Date));

create table responsibilities (
     FacilityID char(6) not null,
     Date date not null,
     EmployeeNID varchar(256) not null,
     constraint IDresponsibility primary key (EmployeeNID, FacilityID, Date));

create table REVIEWS (
     ReviewID char(8) not null,
     Rating decimal(1,0) not null,
     Date date not null,
     Time time not null,
     Description varchar(1000),
     Account varchar(256) not null,
     ParkServiceID char(6) not null,
     constraint IDRECENSIONE primary key (ReviewID),
     constraint IDREVIEW unique (Account, ParkServiceID),
     constraint RATING_FORMAT check (Rating between 1 and 5));

create table RIDE_DETAILS (
     RideID char(6) not null,
     Status char(1) not null,
     EstimatedWaitTime time,
     constraint RIDEID_CHECK check (RideID like 'RI%'),
     -- if EstimatedWaitTime is null then the ride must not be open. Otherwise, if it is not null then the ride must be open.
     constraint STATUS_CHECK check ((EstimatedWaitTime is not null or (Status in ('C', 'M'))) and (EstimatedWaitTime is null or (Status = 'O'))),
     constraint STATUS_DOMAIN check (Status in ('O', 'M', 'C')),
     constraint FKride_ride_detail_ID primary key (RideID));

create table RIDES (
     RideID char(6) not null,
     Intensity varchar(50) not null,
     Duration time not null,
     MaxSeats int not null,
     MinHeight int unsigned not null,
     MaxHeight int unsigned not null,
     MinWeight int unsigned not null,
     MaxWeight int unsigned not null,
     constraint FKR_ID primary key (RideID),
     constraint DURATION_CHECK check (Duration != CAST('00:00:00' AS time)),
     constraint RIDEID_FORMAT check (RideID like 'RI%'),
     constraint RIDES_MAX_SEATS_DOMAIN check (MaxSeats > 0),
     constraint HEIGHT_VALUES_CONSISTENCY check (MinHeight < MaxHeight),
     constraint WEIGHT_VALUES_CONSISTENCY check (MinWeight < MaxWeight));

create table STAFF (
     StaffID char(72) not null,
     NationalID varchar(256) not null,
     Email varchar(256) not null,
     Name varchar(256) not null,
     Surname varchar(256) not null,
     DoB date not null,
     BirthPlace varchar(256) not null,
     Gender char(1) not null,
     Role varchar(256),
     IsAdmin boolean not null,
     IsEmployee boolean not null,
     constraint IDSTAFF primary key (NationalID),
     constraint IDSTAFF_1 unique (StaffID),
     constraint FKR_ID unique (Email), 
     constraint GENDER_DOMAIN check (Gender in ('M', 'F')),
     constraint ROLE_CHECK check ((Role is null and isAdmin = true) or (Role is not null and isEmployee = true)),
     constraint FLAGS_CONSISTENCY check ((isAdmin = true and isEmployee = false) or (isAdmin = false and isEmployee = true)));

create table TICKET_TYPES (
     Year int not null,
     Price decimal(5,2) not null,
     Type varchar(50) not null,
     Category varchar(50) not null,
     Duration int not null,
     constraint IDTICKET_TYPE primary key (Year, Type, Category),
     constraint TYPE_DOMAIN check (Type in ('Single day ticket', 'Season ticket')),
     constraint TYPE_CONSISTENCY check ((Type = 'Single day ticket' and Duration = 1) or (Type = 'Season ticket' and Duration > 1)),
     constraint DURATION_DOMAIN check (Duration >= 1),
     constraint CATEGORY_DOMAIN check (Category in ('Senior', 'Kids', 'Adults', 'Disable')),
     constraint PRICE_NON_NEGATIVITY_CHECK check (Price > 0));

create table TICKETS (
     TicketID char(65) not null,
     PurchaseDate date not null,
     ValidOn date,
     ValidUntil date,
     RemainingEntrances int unsigned not null,
     OwnerID char(72) not null,
     constraint IDTICKET_ID primary key (TicketID),
     constraint TICKETID_FORMAT check (TicketID like 'T%'),
     constraint PURCHASE_DATE_CHK check ((ValidOn is not null and PurchaseDate <= ValidOn) or (ValidUntil is not null and PurchaseDate <= ValidUntil)),
     constraint TICKET_TYPE_CHK check ((ValidOn is not null and ValidUntil is null) or (ValidOn is null and ValidUntil is not null)));

create table validations (
     Date date not null,
     TicketID char(65) not null,
     constraint IDvalidations primary key (Date, TicketID));


-- Foreign key constraints
-- ___________________ 

alter table ACCOUNTS add constraint FKpossessions
     foreign key (PermissionType)
     references PERMISSIONS (PermissionType);

alter table attributions add constraint FKatt_TIC_FK
     foreign key (TicketID)
     references TICKETS (TicketID);

alter table attributions add constraint FKatt_TIC_1
     foreign key (Year, Type, Category)
     references TICKET_TYPES (Year, Type, Category);

alter table CONTRACTS add constraint FKhiring
     foreign key (EmployerNID)
     references STAFF (NationalID);

alter table CONTRACTS add constraint FKemployment
     foreign key (EmployeeNID)
     references STAFF (NationalID);

alter table COSTS add constraint FKconnection
     foreign key (ShopID)
     references FACILITIES (FacilityID);

alter table EXHIBITION_DETAILS add constraint FKR
     foreign key (ExhibitionID)
     references PARK_SERVICES (ParkServiceID);

alter table FACILITIES add constraint FKR_FK
     foreign key (FacilityID)
     references PARK_SERVICES (ParkServiceID);

alter table GUESTS add constraint FKR_1_FK
     foreign key (Email)
     references ACCOUNTS (Email);

-- Not allowed in MySQL
-- alter table MAINTENANCES add constraint IDMAINTENANCE_CHK
--     check(exists(select * from responsibilities
--                  where responsibilities.FacilityID = FacilityID and responsibilities.Date = Date));

alter table MAINTENANCES add constraint FKexecution
     foreign key (FacilityID)
     references FACILITIES (FacilityID);

alter table PICTURES add constraint FKrepresent
     foreign key (ParkServiceID)
     references PARK_SERVICES (ParkServiceID);

alter table responsibilities add constraint FKres_STA
     foreign key (EmployeeNID)
     references STAFF (NationalID);

alter table responsibilities add constraint FKres_MAI
     foreign key (FacilityID, Date)
     references MAINTENANCES (FacilityID, Date);

alter table REVIEWS add constraint FKpublication
     foreign key (Account)
     references ACCOUNTS (Email);

alter table REVIEWS add constraint FKreference
     foreign key (ParkServiceID)
     references PARK_SERVICES (ParkServiceID);

alter table RIDE_DETAILS add constraint FKride_ride_detail_FK
     foreign key (RideID)
     references RIDES (RideID);

-- Not allowed in MySQL
-- alter table RIDES add constraint FKR_CHK
--     check(exists(select * from RIDE_DETAILS
--                  where RIDE_DETAILS.RideID = RideID)); 

alter table RIDES add constraint FKR_FKR
     foreign key (RideID)
     references FACILITIES (FacilityID);

alter table STAFF add constraint FKR_FKS
     foreign key (Email)
     references ACCOUNTS (Email);

alter table TICKET_TYPES add constraint FKcomposition
     foreign key (Year)
     references PRICE_LISTS (Year);

-- Not allowed in MySQL
-- alter table TICKETS add constraint IDTICKET_CHK
--     check(exists(select * from attributions
--                  where attributions.TicketID = TicketID)); 

alter table TICKETS add constraint FKpurchase
     foreign key (OwnerID)
     references GUESTS (GuestID);

alter table validations add constraint FKval_TIC
     foreign key (TicketID)
     references TICKETS (TicketID);

alter table validations add constraint FKval_PUN
     foreign key (Date)
     references PUNCH_DATES (Date);