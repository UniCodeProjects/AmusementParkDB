-- Sample permissions for testing, they do not reflect actual permissions.
insert into permissions
values ("Admin", "dcff273f-babc0aa5.babc0aa5.babc0aa5.babc0aa5/b12dbd8c-babc0aa5.babc0aa5.babc0aa5.babc0aa5.babc0aa5/3d7457cb-babc0aa5.babc0aa5.babc0aa5.babc0aa5/5995fb65-babc0aa5/faad4871-babc0aa5.babc0aa5.babc0aa5/301e489d-babc0aa5.babc0aa5.babc0aa5.babc0aa5/82b83714-babc0aa5.babc0aa5.babc0aa5.babc0aa5/afe0632b-babc0aa5/7b2a91e-babc0aa5.babc0aa5.babc0aa5/ff05a55e-babc0aa5.babc0aa5.babc0aa5.babc0aa5/73a50340-babc0aa5/db14673a-babc0aa5.babc0aa5.babc0aa5.babc0aa5.babc0aa5.babc0aa5.babc0aa5/49a03bd7-babc0aa5.babc0aa5.babc0aa5.babc0aa5/2872af73-babc0aa5/f9c13600-babc0aa5/f68bc462-babc0aa5/d7fffc07-babc0aa5.babc0aa5.babc0aa5.babc0aa5/80de8791-babc0aa5.babc0aa5.babc0aa5.babc0aa5"),
	   ("Staff", "dcff273f-c99b0764.8f5b9f42.bb6bb0ea.5bd9988d/b12dbd8c-e9590d11.3575709e.48d6ca62.a37e64d.8565db80/3d7457cb-664c351e.9d20d71f.17b11394.a6d85f7/5995fb65-fa34bf9d.55153709.3fc20cb2.abb0d312.d172b09f.3bf0dffa.7eb15495.37bed3f4/faad4871-6845a29e.6845a29e.6845a29e/301e489d-6845a29e.6845a29e.6845a29e.6845a29e/82b83714-de5ce536.e4b5e2b1.8df42cc5.114bbfc9.83d45565.e34a64b1.ed5c5af0.326138e7/afe0632b-6845a29e/7b2a91e-c3ca99a9.f3a3d30f.42130661/ff05a55e-6845a29e.6845a29e.6845a29e.6845a29e/73a50340-886ff2c8/db14673a-cfd86dd9.83d45565.e34a64b1.ed5c5af0.326138e7.d96d7810.babc0aa5.78d87c0f.dc59ac62.ac57b226.5c8bda3e.babc0aa5/49a03bd7-cfd86dd9.83d45565.e34a64b1.ed5c5af0.326138e7.936f993f.21018064.5ed3ad4d.973efd71/2872af73-15b122ca/f9c13600-9e576dc1.24225ecf.c1ae7720.7a9e16fc.356900a0/f68bc462-11ee87f2.24225ecf.c1ae7720.7a9e16fc.356900a0/d7fffc07-6845a29e.6845a29e.6845a29e.6845a29e/80de8791-98e9963f.79538b18.98f2db45.89ef0a2f"),
       ("Guest", "dcff273f-107d3b30.8f5b9f42.47cafeb0.d7464cb/b12dbd8c-e9590d11.70eca763.6114ac7c.287daa1b.8565db80/3d7457cb-6845a29e.6845a29e.6845a29e.6845a29e/5995fb65-6845a29e/faad4871-6845a29e.6845a29e.6845a29e/301e489d-6845a29e.6845a29e.6845a29e.6845a29e/82b83714-bb5fa20b.6fc83a5b.6e7d21d7.1266d9a4.4662d341.6a95b33f.41e5a522.bf693592/afe0632b-6845a29e/7b2a91e-6845a29e.6845a29e.6845a29e/ff05a55e-6845a29e.6845a29e.6845a29e.6845a29e/73a50340-6845a29e/db14673a-cfd86dd9.4662d341.6a95b33f.41e5a522.bf693592.79be4259.cfd86dd9.6b8015d8.8c3ad1fb.c24d52d7.2a95030c.cfd86dd9/49a03bd7-6845a29e.6845a29e.6845a29e.6845a29e/2872af73-6845a29e/f9c13600-ddda3c6f.c2d9230f.dddb6d01.be5033f3.b6846e9a/f68bc462-2284d11b.c2d9230f.dddb6d01.be5033f3.b6846e9a/d7fffc07-6845a29e.6845a29e.6845a29e.6845a29e/80de8791-b2395871.fa701e2d.99d8c6c.394051ea");

insert into accounts
values ("mariorossi@gmail.com", "mario.rossi", "A@BbCc11", "Admin"),
       ("andreaverdi@gmail.com", "andrea.verdi", "A@BbCc22", "Staff"), 
       ("mariagialli@gmail.com", "maria.gialli", "A@BbCc33", "Staff"),
       ("leonardorossi@gmail.com", "leonardo.rossi", "A@BbCc44", "Staff"),
       ("sofiaverdi@gmail.com", "sofia.verdi", "A@BbCc55", "Staff"),
       ("alessandrogialli@gmail.com", "alessandro.gialli", "A@BbCc66", "Guest"),
       ("tommasorusso@gmail.com", "tommaso.russo", "A@BbCc77", "Guest"),
       ("francescoesposito@gmail.com", "francesco.esposito", "A@BbCc88", "Guest"),
       ("riccardoferrari@gmail.com", "riccardo.ferrari", "A@BbCc99", "Guest");
       
insert into guests
values ("G-001", "alessandrogialli@gmail.com", "Alessandro", "Gialli"),
       ("G-002", "tommasorusso@gmail.com", "Tommaso", "Russo"),
       ("G-003", "francescoesposito@gmail.com", "Francesco", "Esposito"),
       ("G-004", "riccardoferrari@gmail.com", "Riccardo", "Ferrari");
       
insert into staff
values ("A-001", "MRARSS77E15A944I", "mariorossi@gmail.com", "Mario", "Rossi", '1977-05-15', "Bologna", 'M', null, true, false),
       ("E-003", "VRDNDR66T22F839F", "andreaverdi@gmail.com", "Andrea", "Verdi", '1966-12-22', "Napoli", "M", "engineer", false, true),
       ("E-004", "GLLMRA79H49H501I", "mariagialli@gmail.com", "Maria", "Gialli", '1979-06-09', "Roma", "F", "chef", false, true),
       ("E-001", "RSSLRD89L17C573J", "leonardorossi@gmail.com", "Leonardo", "Rossi", '1989-07-17', "Cesena", "M", "waiter", false, true),
       ("E-002", "VRDSFO93R45D704B", "sofiaverdi@gmail.com", "Sofia", "Verdi", '1993-10-05', "Forlì", "F", "engineer", false, true);

insert into contracts 
values ("C-001", '2021-12-10', '2022-01-01', null, 1100.00, "MRARSS77E15A944I", "RSSLRD89L17C573J"),
       ("C-002", '2023-05-04', '2023-06-01', '2024-01-31', 1600.00, "MRARSS77E15A944I", "VRDSFO93R45D704B"),
       ("C-003", '2023-02-22', '2023-04-01', '2024-04-30', 1500.00, "MRARSS77E15A944I", "GLLMRA79H49H501I"),
       ("C-004", '2023-08-19', '2023-09-01', null, 1200.00, "MRARSS77E15A944I", "VRDNDR66T22F839F");

insert into price_lists
values (2023),
	   (2024);

insert into park_services
values ("RI-001", "Ride1", 4.5, 2, "type1", null, false),
       ("RI-002", "Ride2", 4, 1, "type1", null, false),
       ("RI-003", "Ride3", 5, 1, "type2", null, false),
       ("EX-001", "Exhibition1", 4.5, 2, "type3", null, true),
       ("EX-002", "Exhibition2", 5, 1, "type4", null, true),
       ("SH-001", "Shop1", 4.5, 2, "type5", null, false),
       ("SH-002", "Shop2", 4, 1, "type5", null, false);

insert into facilities
values ("RI-001", '09:00:00', '19:00:00', false),
       ("RI-002", '09:00:00', '19:00:00', false),
       ("RI-003", '09:00:00', '19:00:00', false),
       ("SH-001", '09:00:00', '19:00:00', true),
       ("SH-002", '09:00:00', '19:00:00', true);

insert into pictures
values ("img/ride1.jpg", "RI-001"),
       ("img/ride2.jpg", "RI-002"),
       ("img/ride3.jpg", "RI-003"),
       ("img/exhibition1.jpg", "EX-001"),
       ("img/exhibition2.jpg", "EX-002"),
       ("img/shop1.jpg", "SH-001"),
       ("img/shop2.jpg", "SH-002");
       
insert into rides
values ("RI-001", "intensity1", '00:03:00', 30, 100, 200, 40, 200),
       ("RI-002", "intensity1", '00:02:30', 25, 100, 200, 20, 150),
       ("RI-003", "intensity2", '00:02:00', 40, 100, 190, 20, 160);
       
insert into ride_details
values ("RI-001", "O", '00:00:00'),
       ("RI-002", "O", '00:05:00'),
       ("RI-003", "O", '00:10:00');
       
insert into exhibition_details
values ("EX-001", '2023-09-15', '16:00:00', 200, null),
       ("EX-001", '2023-09-02', '14:00:00', 150, 120),
       ("EX-002", '2023-09-01', '15:00:00', 300, 200),
       ("EX-002", '2023-09-20', '15:30:00', 400, null);
       
insert into costs
values ("SH-001", 14500.00, 5000.00, 9, 2023),
	   ("SH-001", 10000.00, 3000.00, 8, 2023),
       ("SH-001", 17000.00, 2000.00, 7, 2023),
       ("SH-001", 20000.00, 4000.00, 6, 2023),
       ("SH-002", 15500.00, 6000.00, 9, 2023),
       ("SH-002", 15000.00, 3500.00, 8, 2023),
       ("SH-002", 14500.00, 2000.00, 7, 2023),
       ("SH-002", 11000.00, 2450.00, 6, 2023);
       
insert into reviews
values ("b0b7bc8b", 5, '2023-09-08', '15:30:04', null, "andreaverdi@gmail.com", "RI-001"),
       ("7602814a", 4, '2023-09-01', '22:12:55', null, "tommasorusso@gmail.com", "RI-001"),
       ("2595bb41", 4, '2023-07-22', '10:01:33', null, "tommasorusso@gmail.com", "RI-002"),
       ("6e946d3", 5, '2023-06-27', '16:25:00', null, "francescoesposito@gmail.com", "RI-003"),
       ("924fa3cf", 5, '2023-05-13', '11:22:54', null, "mariagialli@gmail.com", "EX-001"),
       ("240bace7", 4, '2023-03-03', '17:31:45', null, "tommasorusso@gmail.com", "EX-001"),
       ("a1141a57", 5, '2023-09-03', '18:20:13', null, "francescoesposito@gmail.com", "EX-002"),
       ("cb362766", 5, '2023-01-14', '19:43:43', null, "andreaverdi@gmail.com", "SH-001"),
       ("2f150ea4", 4, '2023-01-19', '16:14:59', null, "mariagialli@gmail.com", "SH-001"),
       ("99fafe0f", 4, '2023-05-19', '19:19:19', null, "tommasorusso@gmail.com", "SH-002");
       
insert into maintenances
values ("RI-001", 500.00, "maintenance1", '2023-06-10'),
       ("RI-001", 100.00, "maintenance2", '2023-05-15'),
       ("RI-002", 1000.00, "maintenance3", '2023-07-17'),
       ("RI-002", 550.00, "maintenance4", '2023-09-15'),
       ("RI-003", 400.00, "maintenance5", '2023-08-05'),
       ("RI-003", 450.00, "maintenance6", '2023-09-25');
       
insert into responsibilities
values ("RI-001", '2023-06-10', "VRDSFO93R45D704B"),
       ("RI-001", '2023-06-10', "GLLMRA79H49H501I"),
       ("RI-001", '2023-05-15', "GLLMRA79H49H501I"),
       ("RI-002", '2023-07-17', "VRDSFO93R45D704B"),
       ("RI-002", '2023-07-17', "GLLMRA79H49H501I"),
       ("RI-002", '2023-09-15', "GLLMRA79H49H501I"),
       ("RI-003", '2023-08-05', "VRDSFO93R45D704B"),
       ("RI-003", '2023-09-25', "VRDSFO93R45D704B");
       
insert into tickets
values ("T-001", '2023-05-05', '2023-05-05', null, 0, "G-001"),
       ("T-002", '2023-07-10', '2023-07-19', null, 0, "G-002"),
       ("T-003", '2023-03-06', null, '2023-05-06', 7, "G-003"),
       ("T-004", '2023-09-01', null, '2023-12-01', 8, "G-004"),
       ("T-005", '2023-08-10', '2023-08-10', null, 0, "G-002"),
       ("T-006", '2023-06-24', '2023-07-01', null, 0, "G-003");

insert into ticket_types
values (2023, 25.00, "single day ticket", "kids", 1),
       (2023, 50.00, "single day ticket", "adults", 1),
       (2023, 25.00, "single day ticket", "senior", 1),
       (2023, 25.00, "single day ticket", "disable", 1),
       (2023, 75.00, "season ticket", "kids", 10),
       (2023, 150.00, "season ticket", "adults", 10),
       (2023, 75.00, "season ticket", "senior", 10),
       (2023, 75.00, "season ticket", "disable", 10),
       (2024, 20.00, "single day ticket", "kids", 1),
       (2024, 40.00, "single day ticket", "adults", 1),
       (2024, 20.00, "single day ticket", "senior", 1),
       (2024, 15.00, "single day ticket", "disable", 1),
       (2024, 70.00, "season ticket", "kids", 10),
       (2024, 135.00, "season ticket", "adults", 10),
       (2024, 65.00, "season ticket", "senior", 10),
       (2024, 60.00, "season ticket", "disable", 10);
       
insert into attributions
values ("T-001", 2023, "single day ticket", "adults"),
       ("T-002", 2023, "single day ticket", "adults"),
       ("T-003", 2023, "season ticket", "adults"),
       ("T-004", 2023, "season ticket", "adults"),
       ("T-005", 2023, "single day ticket", "adults"),
       ("T-006", 2023, "single day ticket", "adults");
       
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
       
insert into monthly_recaps
values ('2023-01-01', 100000.00),
	   ('2023-02-01', 125000.00),
       ('2023-03-01', 50000.00),
       ('2023-04-01', 75460.00),
       ('2023-05-01', 74085.00);