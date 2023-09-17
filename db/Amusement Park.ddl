create database Amusement_Park;
use Amusement_Park;

-- Tables Section
-- _____________ 

create table ACCOUNTS (
     Email varchar(256) not null,
     Username varchar(30),
     Password varchar(30),
     constraint IDACCOUNT primary key (Email),
     constraint IDACCOUNT_1 unique (Username));

create table attributions (
     TicketID char(65) not null,
     Year int not null,
     Type varchar(50) not null,
     Target varchar(50) not null,
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
     constraint IDCONTRACT unique (SubscriptionDate, EmployeeNID));

create table COSTS (
     ShopID char(6) not null,
     Revenue decimal(8,2) not null,
     Expenses decimal(8,2) not null,
     Month int not null,
     Year int not null,
     constraint IDCOST primary key (ShopID, Month, Year));

create table MONTHLY_RECAPS (
     Date date not null,
     Revenue decimal(9,2) not null,
     constraint IDMONTHLY_RECAP primary key (Date));

create table EXHIBITION_DETAILS (
     ExhibitionID char(6) not null,
     Date date not null,
     Time time not null,
     MaxSeats int not null,
     Spectators int,
     constraint IDEXHIBITION_DETAIL primary key (ExhibitionID, Date, Time));

create table FACILITIES (
     FacilityID char(6) not null,
     OpeningTime time not null,
     ClosingTime time not null,
     IsShop boolean not null,
     constraint FKR_ID primary key (FacilityID));

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
     constraint IDMAINTENANCE_ID primary key (FacilityID, Date));

create table PARK_SERVICES (
     ParkServiceID char(6) not null,
     Name varchar(256) not null,
     AvgRating decimal(2,1) not null,
     NumReviews int not null,
     Type varchar(256) not null,
     Description varchar(1000),
     IsExhibition boolean not null,
     constraint IDPARK_SERVICE primary key (ParkServiceID),
     constraint IDPARK_SERVICE_1 unique (Name));

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
     ReviewID int not null,
     Rating decimal(1,0) not null,
     Date date not null,
     Time time not null,
     Description varchar(1000),
     Account varchar(256) not null,
     ParkServiceID char(6) not null,
     constraint IDRECENSIONE primary key (ReviewID),
     constraint IDREVIEW unique (Account, ParkServiceID));

create table RIDES (
     RideID char(6) not null,
     Intensity varchar(50) not null,
     Duration time not null,
     MaxSeats int not null,
     MinHeight int not null,
     MaxHeight int not null,
     MinWeight int not null,
     MaxWeight int not null,
     constraint FKR_ID primary key (RideID));

create table RIDE_DETAILS (
     RideID char(6) not null,
     Status char(1) not null,
     EstimatedWaitTime time,
     constraint FKride_ride_detail_ID primary key (RideID));

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
     constraint FKR_ID unique (Email));

create table TICKETS (
     TicketID char(65) not null,
     PurchaseDate date not null,
     ValidOn date,
     ValidUntil date,
     RemainingEntrances int not null,
     OwnerID char(72) not null,
     constraint IDTICKET_ID primary key (TicketID));

create table TICKET_TYPES (
     Year int not null,
     Price decimal(5,2) not null,
     Type varchar(50) not null,
     Target varchar(50) not null,
     Duration int not null,
     constraint IDTICKET_TYPE primary key (Year, Type, Target));

create table validations (
     Date date not null,
     TicketID char(65) not null,
     constraint IDvalidations primary key (Date, TicketID));


-- Constraints Section
-- ___________________ 

alter table attributions add constraint FKatt_TIC_FK
     foreign key (TicketID)
     references TICKETS (TicketID);

alter table attributions add constraint FKatt_TIC_1
     foreign key (Year, Type, Target)
     references TICKET_TYPES (Year, Type, Target);

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

-- Not allowed in MySQL
-- alter table RIDES add constraint FKR_CHK
--     check(exists(select * from RIDE_DETAILS
--                  where RIDE_DETAILS.RideID = RideID)); 

alter table RIDES add constraint FKRIDE_FK
     foreign key (RideID)
     references FACILITIES (FacilityID);

alter table RIDE_DETAILS add constraint FKride_ride_detail_FK
     foreign key (RideID)
     references RIDES (RideID);

alter table STAFF add constraint FKSTAFF_FK
     foreign key (Email)
     references ACCOUNTS (Email);

-- Not allowed in MySQL
-- alter table TICKETS add constraint IDTICKET_CHK
--     check(exists(select * from attributions
--                  where attributions.TicketID = TicketID)); 

alter table TICKETS add constraint FKpurchase
     foreign key (OwnerID)
     references GUESTS (GuestID);

alter table TICKET_TYPES add constraint FKcomposition
     foreign key (Year)
     references PRICE_LISTS (Year);

alter table validations add constraint FKval_TIC
     foreign key (TicketID)
     references TICKETS (TicketID);

alter table validations add constraint FKval_PUN
     foreign key (Date)
     references PUNCH_DATES (Date);