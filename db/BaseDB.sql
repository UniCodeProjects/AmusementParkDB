insert into accounts
values ("mariorossi@mail.com", "mario.rossi", "A@BbCc11", "Admin"),
       ("andreaverdi@mail.com", "andrea.verdi", "A@BbCc22", "Staff"), 
       ("mariagialli@mail.com", "maria.gialli", "A@BbCc33", "Staff"),
       ("leonardorossi@mail.com", "leonardo.rossi", "A@BbCc44", "Staff"),
       ("sofiaverdi@mail.com", "sofia.verdi", "A@BbCc55", "Staff"),
       ("alessandrogialli@mail.com", "alessandro.gialli", "A@BbCc66", "Guest"),
       ("tommasorusso@mail.com", "tommaso.russo", "A@BbCc77", "Guest"),
       ("francescoesposito@mail.com", "francesco.esposito", "A@BbCc88", "Guest"),
       ("riccardoferrari@mail.com", "riccardo.ferrari", "A@BbCc99", "Guest"),
       ("pietrobianchi@mail.com", "pietro.bianchi", "@Bcdefg1", "Staff"),
       ("martapizzulla@mail.com", "marta.pizzulla", "@Bcdefg2", "Staff");
       
insert into guests
values ("Ale-Gia-4b160428", "alessandrogialli@mail.com", "Alessandro", "Gialli"),
       ("Tom-Rus-8d031c19", "tommasorusso@mail.com", "Tommaso", "Russo"),
       ("Fra-Esp-e28cee91", "francescoesposito@mail.com", "Francesco", "Esposito"),
       ("Ric-Fer-bcba6597", "riccardoferrari@mail.com", "Riccardo", "Ferrari");
       
insert into staff
values ("Mar-Ros-254ddfbc", "MRARSS77E15A944I", "mariorossi@mail.com", "Mario", "Rossi", '1977-05-15', "Bologna", 'M', null, true, false),
       ("And-Ver-e5bbe886", "VRDNDR66T22F839F", "andreaverdi@mail.com", "Andrea", "Verdi", '1966-12-22', "Napoli", "M", "engineer", false, true),
       ("Mar-Gia-f592ab85", "GLLMRA79H49H501I", "mariagialli@mail.com", "Maria", "Gialli", '1979-06-09', "Roma", "F", "chef", false, true),
       ("Leo-Ros-fc10576d", "RSSLRD89L17C573J", "leonardorossi@mail.com", "Leonardo", "Rossi", '1989-07-17', "Cesena", "M", "waiter", false, true),
       ("Sof-Ver-f00852e9", "VRDSFO93R45D704B", "sofiaverdi@mail.com", "Sofia", "Verdi", '1993-10-05', "Forlì", "F", "engineer", false, true),
       ("Pie-Bia-389ce515", "BNCPTR82S05A944O", "pietrobianchi@mail.com", "Pietro", "Bianchi", '1982-11-05', "Bologna", "M", "ride attendant", false, true),
       ("Mar-Piz-5811e0c", "PZZMRT99E68G273X", "martapizzulla@mail.com", "Marta", "Pizzulla", '1999-05-28', "Palermo", "F", "chef", false, true);

insert into contracts 
values ("C-f2afe4e1", '2021-12-10', '2022-01-01', null, 1100.00, "MRARSS77E15A944I", "RSSLRD89L17C573J"),
       ("C-8e76b68d", '2023-05-04', '2023-06-01', '2024-01-31', 1600.00, "MRARSS77E15A944I", "VRDSFO93R45D704B"),
       ("C-453dabdd", '2023-02-22', '2023-04-01', '2024-04-30', 1500.00, "MRARSS77E15A944I", "GLLMRA79H49H501I"),
       ("C-7882a6be", '2023-08-19', '2023-09-01', null, 1200.00, "MRARSS77E15A944I", "VRDNDR66T22F839F"),
       ("C-f59d9d35", '2024-01-01', '2024-05-01', '2025-04-30', 1500.00, "MRARSS77E15A944I", "BNCPTR82S05A944O"),
       ("C-cccfe072", '2024-02-01', '2024-02-01', null, 2000.00, "MRARSS77E15A944I", "PZZMRT99E68G273X");

insert into price_lists
values (2023),
	   (2024);

insert into park_services
values ("RI-3c6dfaa5", "Nitro Blast", 4.5, 2, "Launched coaster", null, false),
       ("RI-e607d536", "Phantom Flyer", 4, 1, "Inverted coaster", null, false),
       ("RI-5c53cbe6", "Tidal Twister", 5, 1, "Water ride", null, false),
       ("EX-39234f38", "Adventure Avenue", 4.5, 2, "Theatre play", null, true),
       ("EX-dc28f34b", "Extreme Evolution", 5, 1, "Stunt show", null, true),
       ("SH-eaa716ef", "Burger Town", 4.5, 2, "Restaurant", null, false),
       ("SH-d08f831a", "Gizmo Grove", 4, 1, "Gadgets", null, false),
       ("RI-cadf1460", "Thunderbolt Twister", 4, 2, "Launched coaster", "This ultimate roller coaster adventure will leave you breathless and craving more. Are you ready to brave the storm? Ride the Thunderbolt Twister today!", false),
       ("RI-b199d969", "Galactic Whirl", 5, 1, "Spinning ride", null, false),
       ("RI-222fe4ef", "Jungle Rapids", 4.5, 2, "Water ride", null, false),
       ("EX-7daef637", "Wheels of Fury", 5, 1, "Stunt show", null, true);

insert into facilities
values ("RI-3c6dfaa5", '09:00:00', '19:00:00', false),
       ("RI-e607d536", '09:00:00', '19:00:00', false),
       ("RI-5c53cbe6", '09:00:00', '19:00:00', false),
       ("SH-eaa716ef", '09:00:00', '19:00:00', true),
       ("SH-d08f831a", '09:00:00', '19:00:00', true),
       ("RI-cadf1460", '09:00:00', '19:00:00', false),
       ("RI-b199d969", '09:00:00', '19:00:00', false),
       ("RI-222fe4ef", '09:00:00', '19:00:00', false);

insert into pictures
values ("img/NitroBlast.jpg", "RI-3c6dfaa5"),
       ("img/PhantomFlyer.jpg", "RI-e607d536"),
       ("img/TidalTwister.jpg", "RI-5c53cbe6"),
       ("img/AdventureAvenue.jpg", "EX-39234f38"),
       ("img/ExtremeEvolution.jpg", "EX-dc28f34b"),
       ("img/BurgerTown.jpg", "SH-eaa716ef"),
       ("img/GizmoGrove.jpg", "SH-d08f831a"),
       ("img/ThunderboltTwister.jpg", "RI-cadf1460"),
       ("img/GalacticWhirl.jpg", "RI-b199d969"),
       ("img/JungleRapids.jpg", "RI-222fe4ef");
       
insert into rides
values ("RI-3c6dfaa5", "Extreme", '00:03:00', 30, 100, 200, 40, 200),
       ("RI-e607d536", "Extreme", '00:02:30', 25, 100, 200, 20, 150),
       ("RI-5c53cbe6", "Mild", '00:02:00', 40, 100, 190, 20, 160),
       ("RI-cadf1460", "Extreme", '00:02:00', 24, 120, 200, 40, 200),
       ("RI-b199d969", "Moderate", '00:03:00', 32, 100, 200, 40, 200),
       ("RI-222fe4ef", "Mild", '00:05:00', 20, 100, 200, 20, 200);
       
insert into ride_details
values ("RI-3c6dfaa5", "O", '00:00:00'),
       ("RI-e607d536", "O", '00:05:00'),
       ("RI-5c53cbe6", "O", '00:10:00'),
       ("RI-cadf1460", "O", '00:20:00'),
       ("RI-b199d969", "O", '00:15:00'),
       ("RI-222fe4ef", "O", '00:10:00');
       
insert into exhibition_details
values ("EX-39234f38", '2023-09-15', '16:00:00', 200, null),
       ("EX-39234f38", '2023-09-02', '14:00:00', 150, 120),
       ("EX-dc28f34b", '2023-09-01', '15:00:00', 300, 200),
       ("EX-dc28f34b", '2023-09-20', '15:30:00', 400, null),
       ("EX-7daef637", '2024-06-10', '16:00:00', 100, null),
       ("EX-7daef637", '2024-06-25', '11:30:00', 100, null),
       ("EX-7daef637", '2023-09-18', '16:30:00', 85, 60);
       
insert into costs
values ("SH-eaa716ef", 14500.00, 5000.00, 9, 2023),
	   ("SH-eaa716ef", 10000.00, 3000.00, 8, 2023),
       ("SH-eaa716ef", 17000.00, 2000.00, 7, 2023),
       ("SH-eaa716ef", 20000.00, 4000.00, 6, 2023),
       ("SH-d08f831a", 15500.00, 6000.00, 9, 2023),
       ("SH-d08f831a", 15000.00, 3500.00, 8, 2023),
       ("SH-d08f831a", 14500.00, 2000.00, 7, 2023),
       ("SH-d08f831a", 11000.00, 2450.00, 6, 2023);
       
insert into reviews
values ("b0b7bc8b", 5, '2023-09-08', '15:30:04', null, "Ale-Gia-4b160428", "RI-3c6dfaa5"),
       ("7602814a", 4, '2023-09-01', '22:12:55', null, "Tom-Rus-8d031c19", "RI-3c6dfaa5"),
       ("2595bb41", 4, '2023-07-22', '10:01:33', null, "Tom-Rus-8d031c19", "RI-e607d536"),
       ("6e946d3", 5, '2023-06-27', '16:25:00', null, "Fra-Esp-e28cee91", "RI-5c53cbe6"),
       ("924fa3cf", 5, '2023-05-13', '11:22:54', null, "Ric-Fer-bcba6597", "EX-39234f38"),
       ("240bace7", 4, '2023-03-03', '17:31:45', null, "Tom-Rus-8d031c19", "EX-39234f38"),
       ("a1141a57", 5, '2023-09-03', '18:20:13', null, "Fra-Esp-e28cee91", "EX-dc28f34b"),
       ("cb362766", 5, '2023-01-14', '19:43:43', null, "Ale-Gia-4b160428", "SH-eaa716ef"),
       ("2f150ea4", 4, '2023-01-19', '16:14:59', null, "Ric-Fer-bcba6597", "SH-eaa716ef"),
       ("99fafe0f", 4, '2023-05-19', '19:19:19', null, "Tom-Rus-8d031c19", "SH-d08f831a"),
       ("a3cfbd18", 4, '2023-12-12', '15:04:21', "I really enjoyed it!", "Tom-Rus-8d031c19", "RI-cadf1460"),
       ("a54b1f89", 4, '2024-05-15', '17:25:19', null, "Fra-Esp-e28cee91", "RI-cadf1460"),
       ("7ebdc34a", 5, '2024-04-01', '19:30:37', "This spinning ride is amazing! It feels very safe. The staff were very friendly and efficient, A must-try for anyone visiting the park. Can't wait to ride it again!", "Ale-Gia-4b160428", "RI-b199d969"),
       ("b40df21", 4, '2023-09-10', '20:27:55', null, "Ric-Fer-bcba6597", "RI-222fe4ef"),
       ("e2298fc6", 5, '2024-05-25', '11:45:13', null, "Tom-Rus-8d031c19", "RI-222fe4ef"),
       ("b10ef4e8", 5, '2023-09-19', '19:44:12', null, "Fra-Esp-e28cee91", "EX-7daef637");
       
insert into maintenances
values ("RI-3c6dfaa5", 500.00, "Track maintenance.", '2023-06-10'),
       ("RI-3c6dfaa5", 100.00, "Inspected and lubricated all mechanical components, updated control systems, and conducted successful load and operational tests.", '2023-05-15'),
       ("RI-e607d536", 1000.00, "Track maintenance. Replaced the brakes.", '2023-07-17'),
       ("RI-e607d536", 550.00, "Inspected and lubricated all mechanical components, updated control systems, and conducted successful load and operational tests.", '2023-09-15'),
       ("RI-5c53cbe6", 400.00, "Substituted some rafts.", '2023-08-05'),
       ("RI-5c53cbe6", 450.00, "Replaced the primary water pump.", '2023-09-25'),
       ("RI-cadf1460", 100.00, "Inspected and lubricated all mechanical components, updated control systems, and conducted successful load and operational tests.", '2024-01-15'),
       ("RI-b199d969", 350.00, "Replaced the main gearbox.", '2024-04-13'),
       ("RI-222fe4ef", 200.00, "Replaced the primary water pump.", '2023-12-19');
       
insert into responsibilities
values ("RI-3c6dfaa5", '2023-06-10', "VRDSFO93R45D704B"),
       ("RI-3c6dfaa5", '2023-06-10', "GLLMRA79H49H501I"),
       ("RI-3c6dfaa5", '2023-05-15', "GLLMRA79H49H501I"),
       ("RI-e607d536", '2023-07-17', "VRDSFO93R45D704B"),
       ("RI-e607d536", '2023-07-17', "GLLMRA79H49H501I"),
       ("RI-e607d536", '2023-09-15', "GLLMRA79H49H501I"),
       ("RI-5c53cbe6", '2023-08-05', "VRDSFO93R45D704B"),
       ("RI-5c53cbe6", '2023-09-25', "VRDSFO93R45D704B"),
       ("RI-cadf1460", '2024-01-15', "VRDSFO93R45D704B"),
       ("RI-b199d969", '2024-04-13', "VRDNDR66T22F839F"),
       ("RI-222fe4ef", '2023-12-19', "VRDNDR66T22F839F");
       
insert into tickets
values ("T-dbbaffb3", '2023-05-05', '2023-05-05', null, 0, "Ale-Gia-4b160428"),
       ("T-ca2ce663", '2023-07-10', '2023-07-19', null, 0, "Tom-Rus-8d031c19"),
       ("T-8e8985f4", '2023-03-06', null, '2023-05-06', 7, "Fra-Esp-e28cee91"),
       ("T-a572544", '2023-09-01', null, '2023-12-01', 8, "Ric-Fer-bcba6597"),
       ("T-ad8830af", '2023-08-10', '2023-08-10', null, 0, "Tom-Rus-8d031c19"),
       ("T-1e8d680f", '2023-06-24', '2023-07-01', null, 0, "Fra-Esp-e28cee91");

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
values ("T-dbbaffb3", 2023, "single day ticket", "adults"),
       ("T-ca2ce663", 2023, "single day ticket", "adults"),
       ("T-8e8985f4", 2023, "season ticket", "adults"),
       ("T-a572544", 2023, "season ticket", "adults"),
       ("T-ad8830af", 2023, "single day ticket", "adults"),
       ("T-1e8d680f", 2023, "single day ticket", "adults");
       
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
values ('2023-05-05', "T-dbbaffb3"),
       ('2023-07-19', "T-ca2ce663"),
       ('2023-03-10', "T-8e8985f4"),
       ('2023-04-15', "T-8e8985f4"),
       ('2023-05-05', "T-8e8985f4"),
       ('2023-09-15', "T-a572544"),
       ('2023-11-25', "T-a572544"),
       ('2023-08-10', "T-ad8830af"),
       ('2023-07-01', "T-1e8d680f");
       
insert into monthly_recaps
values ('2023-01-01', 100000.00),
	   ('2023-02-01', 125000.00),
       ('2023-03-01', 50000.00),
       ('2023-04-01', 75460.00),
       ('2023-05-01', 74085.00);
