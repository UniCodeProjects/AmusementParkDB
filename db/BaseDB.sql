insert into accounts
values ("mariorossi@gmail.com", "mario.rossi", "A@BbCc1"),
       ("andreaverdi@gmail.com", "andrea.verdi", "A@BbCc2"), 
       ("mariagialli@gmail.com", "maria.gialli", "A@BbCc3"),
       ("leonardorossi@gmail.com", "leonardo.rossi", "A@BbCc4"),
       ("sofiaverdi@gmail.com", "sofia.verdi", "A@BbCc5"),
       ("alessandrogialli@gmail.com", "alessandro.gialli", "A@BbCc6"),
       ("tommasorusso@gmail.com", "tommaso.russo", "A@BbCc7"),
       ("francescoesposito@gmail.com", "francesco.esposito", "A@BbCc8"),
       ("riccardoferrari@gmail.com", "riccardo.ferrari", "A@BbCc9");
       
insert into guests
values ("G-001", "andreaverdi@gmail.com", "Andrea", "Verdi"),
       ("G-002", "tommasorusso@gmail.com", "Tommaso", "Russo"),
       ("G-003", "francescoesposito@gmail.com", "Francesco", "Esposito"),
       ("G-004", "mariagialli@gmail.com", "Maria", "Gialli");
       
insert into staff
values ("A-001", "MRARSS77E15A944I", "mariorossi@gmail.com", "Mario", "Rossi", '1977-05-15', "Bologna", null, true, false),
       ("E-001", "RSSLRD89L17C573J", "leonardorossi@gmail.com", "Leonardo", "Rossi", '1989-07-17', "Cesena", "waiter", false, true),
       ("E-002", "VRDSFO93R45D704B", "sofiaverdi@gmail.com", "Sofia", "Verdi", '1993-10-05', "Forlì", "engineer", false, true),
       ("E-003", "GLLLSN66T22F839A", "alessandrogialli@gmail.com", "Alessandro", "Gialli", '1966-12-22', "Napoli", "engineer", false, true),
       ("E-004", "FRRRCR79H09H501F", "riccardoferrari@gmail.com", "Riccardo", "Ferrari", '1979-06-09', "Roma", "chef", false, true);

insert into contracts 
values ("C-001", '2021-12-10', '2022-01-01', null, 1100.00, "MRARSS77E15A944I", "RSSLRD89L17C573J"),
       ("C-002", '2023-05-04', '2023-06-01', '2024-01-01', 1600.00, "MRARSS77E15A944I", "VRDSFO93R45D704B"),
       ("C-003", '2023-02-22', '2023-04-01', '2024-04-01', 1500.00, "MRARSS77E15A944I", "GLLLSN66T22F839A"),
       ("C-004", '2023-08-19', '2023-09-01', null, 1200.00, "MRARSS77E15A944I", "FRRRCR79H09H501F");

insert into price_lists
values (2023);

insert into facilities
values ("R-001", "Ride1", '09:00:00', '19:00:00', 4.5, 2, "type1", false),
       ("R-002", "Ride2", '09:00:00', '19:00:00', 4, 1, "type1", false),
       ("R-003", "Ride3", '09:00:00', '19:00:00', 5, 1, "type2", false),
       ("E-001", "Exhibition1", '09:00:00', '19:00:00', 4.5, 2, "type3", false),
       ("E-002", "Exhibition2", '09:00:00', '19:00:00', 5, 1, "type4", false),
       ("S-001", "Shop1", '09:00:00', '19:00:00', 4.5, 2, "type5", true),
       ("S-002", "Shop2", '09:00:00', '19:00:00', 4, 1, "type5", true);

insert into pictures
values ("path/to/pic1", "R-001"),
       ("path/to/pic2", "R-002"),
       ("path/to/pic3", "R-003"),
       ("path/to/pic4", "E-001"),
       ("path/to/pic5", "E-002"),
       ("path/to/pic6", "S-001"),
       ("path/to/pic7", "S-002");
       
insert into rides
values ("R-001", "intensity1", '00:03:00', 30, null, 100, 200, 40, 200),
       ("R-002", "intensity1", '00:02:30', 25, null, 100, 200, 20, 150),
       ("R-003", "intensity2", '00:02:00', 40, null, 100, 190, 20, 160);
       
insert into ride_details
values ("R-001", "A", '00:00:00'),
       ("R-002", "A", '00:05:00'),
       ("R-003", "A", '00:10:00');
       
insert into exhibitions
values ("E-001", 150, null),
       ("E-002", 300, null);
       
insert into exhibition_details
values ("E-001", '2023-09-15', '16:00:00', null),
       ("E-001", '2023-09-02', '14:00:00', 120),
       ("E-002", '2023-09-01', '15:00:00', 200),
       ("E-002", '2023-09-20', '15:30:00', null);
       
insert into costs
values ("S-001", 10000.00, 3000.00, 08, 2023),
       ("S-001", 17000.00, 2000.00, 07, 2023),
       ("S-001", 20000.00, 4000.00, 06, 2023),
       ("S-002", 15000.00, 3500.00, 08, 2023),
       ("S-002", 14500.00, 2000.00, 07, 2023),
       ("S-002", 11000.00, 2450.00, 06, 2023);
       
insert into reviews
values (000001, 5, '2023-09-08', '15:30:04', null, "R-001", "andreaverdi@gmail.com"),
       (000002, 4, '2023-09-01', '22:12:55', null, "R-001", "tommasorusso@gmail.com"),
       (000003, 4, '2023-07-22', '10:01:33', null, "R-002", "tommasorusso@gmail.com"),
       (000004, 5, '2023-06-27', '16:25:00', null, "R-003", "francescoesposito@gmail.com"),
       (000005, 5, '2023-05-13', '11:22:54', null, "E-001", "mariagialli@gmail.com"),
       (000006, 4, '2023-03-03', '17:31:45', null, "E-001", "tommasorusso@gmail.com"),
       (000007, 5, '2023-09-03', '18:20:13', null, "E-002", "francescoesposito@gmail.com"),
       (000008, 5, '2023-01-14', '19:43:43', null, "S-001", "andreaverdi@gmail.com"),
       (000009, 4, '2023-01-19', '16:14:59', null, "S-001", "mariagialli@gmail.com"),
       (000010, 4, '2023-05-19', '19:19:19', null, "S-002", "tommasorusso@gmail.com");
       
insert into maintenances
values ("R-001", 500.00, "maintenance1", '2023-06-10'),
       ("R-001", 100.00, "maintenance2", '2023-05-15'),
       ("R-002", 1000.00, "maintenance3", '2023-07-17'),
       ("R-003", 400.00, "maintenance4", '2023-08-05');
       
insert into responsibilities
values ("R-001", '2023-06-10', "VRDSFO93R45D704B"),
       ("R-001", '2023-06-10', "GLLLSN66T22F839A"),
       ("R-001", '2023-05-15', "GLLLSN66T22F839A"),
       ("R-002", '2023-07-17', "VRDSFO93R45D704B"),
       ("R-002", '2023-07-17', "GLLLSN66T22F839A"),
       ("R-003", '2023-08-05', "VRDSFO93R45D704B");
       
insert into tickets
values ("T-001", '2023-05-05', '2023-05-05', null, null, "G-001"),
       ("T-002", '2023-07-10', '2023-07-19', null, null, "G-002"),
       ("T-003", '2023-03-06', null, '2023-05-06', 10, "G-003"),
       ("T-004", '2023-09-01', null, '2023-12-01', null, "G-004"),
       ("T-005", '2023-08-10', '2023-08-10', null, null, "G-002"),
       ("T-006", '2023-06-24', '2023-07-01', null, null, "G-003");

insert into ticket_types
values (2023, 25.00, "kids one day ticket"),
       (2023, 50.00, "adults one day ticket"),
       (2023, 25.00, "senior one day ticket"),
       (2023, 75.00, "kids season ticket"),
       (2023, 150.00, "adults season ticket"),
       (2023, 75.00, "senior season ticket");
       
insert into attributions
values ("T-001", 2023, "adults one day ticket"),
       ("T-002", 2023, "adults one day ticket"),
       ("T-003", 2023, "adults season ticket"),
       ("T-004", 2023, "adults season ticket"),
       ("T-005", 2023, "adults one day ticket"),
       ("T-006", 2023, "adults one day ticket");
       
insert into punch_dates
values ('2023-05-05'),
       ('2023-07-19'),
       ('2023-03-10'),
       ('2023-04-15'),
       ('2023-09-15'),
       ('2023-11-25'),
       ('2023-08-10'),
       ('2023-07-01');
       
insert into validations
values ('2023-05-05', "T-001"),
       ('2023-07-19', "T-002"),
       ('2023-03-10', "T-003"),
       ('2023-04-15', "T-003"),
       ('2023-05-05', "T-003"),
       ('2023-09-15', "T-004"),
       ('2023-11-25', "T-004"),
       ('2023-08-10', "T-005"),
       ('2023-07-01', "T-006");