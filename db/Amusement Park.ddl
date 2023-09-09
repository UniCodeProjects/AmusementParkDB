create database Amusement_Park;
use Amusement_Park;

-- Tables Section
-- _____________ 

create table ACCOUNTS (
     Email varchar(256) not null,
     Username varchar(30),
     Password varchar(30),
     constraint IDACCOUNT_ID primary key (Email),
     constraint IDACCOUNT_1 unique (Username));

create table attributions (
     TicketID char(65) not null,
     Year decimal(4) not null,
     Type varchar(50) not null,
     constraint FKatt_TIC_ID primary key (TicketID));

create table CONTRACTS (
     ContractID char(65) not null,
     BeginDate date not null,
     EndDate date,
     Salary decimal(7,2) not null,
     EmployerNID varchar(256) not null,
     EmployeeNID varchar(256) not null,
     constraint IDCONTRATTO primary key (ContractID));

create table COSTS (
     FacilityID char(6) not null,
     Revenue decimal(8,2) not null,
     Expenses decimal(8,2) not null,
     Month decimal(2) not null,
     Year decimal(4) not null,
     constraint IDCOST primary key (FacilityID, Month, Year));

create table EXHIBITIONS (
     ExhibitionID char(6) not null,
     MaxSeats int not null,
     Description varchar(500),
     constraint FKfacility_exhibition_ID primary key (ExhibitionID));

create table EXHIBITION_DETAILS (
     ExhibitionID char(6) not null,
     Date date not null,
     Time time not null,
     Spectators int,
     constraint IDEXHIBITION_DETAILS primary key (ExhibitionID, Date, Time));

create table FACILITIES (
     FacilityID char(6) not null,
     Name varchar(256) not null,
     OpeningTime time not null,
     ClosingTime time not null,
     AvgRating decimal(2,1) not null,
     NumReviews int not null,
     Type varchar(256) not null,
     IsShop boolean not null,
     constraint IDSTRUTTURA primary key (FacilityID),
     constraint IDFACILITY unique (Name));

create table GUESTS (
     GuestID char(72) not null,
     Email varchar(256) not null,
     Name varchar(256) not null,
     Surname varchar(256) not null,
     constraint IDGUEST primary key (GuestID),
     constraint FKguest_ownership_ID unique (Email));

create table MAINTENANCES (
     FacilityID char(6) not null,
     Price decimal(8,2) not null,
     Description varchar(4000) not null,
     Date date not null,
     constraint IDMAINTENANCE_ID primary key (FacilityID, Date));

create table PICTURES (
     Path varchar(256) not null,
     FacilityID char(6) not null,
     constraint IDPICTURE primary key (Path));

create table PRICE_LISTS (
     Year decimal(4) not null,
     constraint IDTIME primary key (Year));

create table PUNCH_DATES (
     Date date not null,
     constraint IDPUNCH_DATE primary key (Date));

create table responsibilities (
     FacilityID char(6) not null,
     Date date not null,
     EmployeeNID varchar(256) not null,
     constraint IDresponsability primary key (EmployeeNID, FacilityID, Date));

create table REVIEWS (
     ReviewID int not null,
     Rating decimal(1) not null,
     Date date not null,
     Time time not null,
     Description varchar(1000),
     FacilityID char(6) not null,
     Account varchar(256) not null,
     constraint IDRECENSIONE primary key (ReviewID),
     constraint IDREVIEW unique (FacilityID, Account));

create table RIDES (
     RideID char(6) not null,
     Intensity varchar(50) not null,
     Duration time not null,
     MaxSeats int not null,
     Description varchar(500),
     MinHeight int not null,
     MaxHeight int not null,
     MinWeight int not null,
     MaxWeight int not null,
     constraint FKfacility_ride_ID primary key (RideID));

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
     Role varchar(256),
     IsAdmin boolean not null,
     IsEmployee boolean not null,
     constraint IDSTAFF primary key (NationalID),
     constraint IDSTAFF_1 unique (StaffID),
     constraint FKstaff_ownership_ID unique (Email));

create table TICKETS (
     TicketID char(65) not null,
     PurchaseDate date not null,
     ValidOn date,
     ValidUntil date,
     Duration decimal(2),
     OwnerID char(72) not null,
     constraint IDTICKET_ID primary key (TicketID));

create table TICKET_TYPES (
     Year decimal(4) not null,
     Price decimal(5,2) not null,
     Type varchar(50) not null,
     constraint IDTICKET_TYPE primary key (Year, Type));

create table validations (
     Date date not null,
     TicketID char(65) not null,
     constraint IDvalidation primary key (Date, TicketID));


-- Constraints Section
-- ___________________ 

-- Not implemented
-- alter table ACCOUNT add constraint IDACCOUNT_CHK
--     check(exists(select * from GUEST
--                  where GUEST.Email = Email)); 

-- Not implemented
-- alter table ACCOUNT add constraint IDACCOUNT_CHK
--     check(exists(select * from STAFF
--                  where STAFF.Email = Email)); 

alter table attributions add constraint FKatt_TIC_FK
     foreign key (TicketID)
     references TICKETS (TicketID);

alter table attributions add constraint FKatt_TIC_1
     foreign key (Year, Type)
     references TICKET_TYPES (Year, Type);

alter table CONTRACTS add constraint FKhiring
     foreign key (EmployerNID)
     references STAFF (NationalID);

alter table CONTRACTS add constraint FKemployement
     foreign key (EmployeeNID)
     references STAFF (NationalID);

alter table COSTS add constraint FKconnection
     foreign key (FacilityID)
     references FACILITIES (FacilityID);

-- Not implemented
-- alter table EXHIBITION add constraint FKfacility-exhibition_CHK
--     check(exists(select * from EXHIBITION_DETAIL
--                  where EXHIBITION_DETAIL.ExhibitionID = ExhibitionID)); 

alter table EXHIBITIONS add constraint FKfacility_exhibition_FK
     foreign key (ExhibitionID)
     references FACILITIES (FacilityID);

alter table EXHIBITION_DETAILS add constraint FKexhibition_exhibition_detail
     foreign key (ExhibitionID)
     references EXHIBITIONS (ExhibitionID);

alter table GUESTS add constraint FKguest_ownership_FK
     foreign key (Email)
     references ACCOUNTS (Email);

-- Not implemented
-- alter table MAINTENANCE add constraint IDMAINTENANCE_CHK
--     check(exists(select * from responsibility
--                  where responsibility.FacilityID = FacilityID and responsibility.Date = Date)); 

alter table MAINTENANCES add constraint FKexecution
     foreign key (FacilityID)
     references FACILITIES (FacilityID);

alter table PICTURES add constraint FKrepresent
     foreign key (FacilityID)
     references FACILITIES (FacilityID);

alter table responsibilities add constraint FKres_STA
     foreign key (EmployeeNID)
     references STAFF (NationalID);

alter table responsibilities add constraint FKres_MAI
     foreign key (FacilityID, Date)
     references MAINTENANCES (FacilityID, Date);

alter table REVIEWS add constraint FKreference
     foreign key (FacilityID)
     references FACILITIES (FacilityID);

alter table REVIEWS add constraint FKpublication
     foreign key (Account)
     references ACCOUNTS (Email);

-- Not implemented
-- alter table RIDE add constraint FKfacility-ride_CHK
--     check(exists(select * from RIDE_DETAIL
--                  where RIDE_DETAIL.RideID = RideID)); 

alter table RIDES add constraint FKfacility_ride_FK
     foreign key (RideID)
     references FACILITIES (FacilityID);

alter table RIDE_DETAILS add constraint FKride_ride_detail_FK
     foreign key (RideID)
     references RIDES (RideID);

alter table STAFF add constraint FKstaff_ownership_FK
     foreign key (Email)
     references ACCOUNTS (Email);

-- Not implemented
-- alter table TICKET add constraint IDTICKET_CHK
--     check(exists(select * from attribution
--                  where attribution.TicketID = TicketID)); 

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