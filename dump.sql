-- H2 2.2.224;
;             
CREATE USER IF NOT EXISTS "STUDENT" SALT '659297f75bbec9bd' HASH 'cf07f4d44bb3686c6f36f96ed5d03be17b03d14998d1fef4709c0c48d48175d1' ADMIN;    
CREATE CACHED TABLE "PUBLIC"."STUDENTI"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 257) NOT NULL,
    "IME" CHARACTER VARYING(20) NOT NULL,
    "GODINA_STUDIJA" INTEGER NOT NULL,
    "SPOL_ID" INTEGER NOT NULL,
    "JMBAG" CHARACTER VARYING(10) NOT NULL,
    "PROSJEK_OCJENA" DECIMAL(5, 2) NOT NULL,
    "FAKULTET_ID" INTEGER NOT NULL
);       
ALTER TABLE "PUBLIC"."STUDENTI" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("ID");     
-- 77 +/- SELECT COUNT(*) FROM PUBLIC.STUDENTI;               
INSERT INTO "PUBLIC"."STUDENTI"("ID", "IME", "GODINA_STUDIJA", "SPOL_ID", "JMBAG", "PROSJEK_OCJENA", "FAKULTET_ID") OVERRIDING SYSTEM VALUE VALUES
(1, U&'Luka Ani\0107', 1, 1, '15284785', 4.88, 1),
(2, U&'David Aramba\0161i\0107', 2, 1, '54772854', 2.45, 3),
(3, U&'Ema Arapovi\0107', 3, 2, '25668921', 1.75, 4),
(4, U&'Mia Badri\0107', 4, 2, '01523640', 3.93, 6),
(5, 'Leon Banovac', 4, 1, '01251693', 2.44, 2),
(6, U&'Mateo Bari\0107', 3, 1, '54210653', 4.84, 5),
(7, U&'Matej Baruk\010di\0107', 3, 1, '58476958', 3.23, 1),
(8, U&'Mia Belo\0161evi\0107', 3, 2, '21054170', 2.56, 3),
(9, U&'Petra Bla\017eevi\0107', 2, 2, '25360125', 3.57, 4),
(10, 'Lucija Boban', 3, 2, '14839051', 4.00, 6),
(11, U&'Borna Bogovi\0107', 4, 1, '25139874', 4.45, 2),
(12, 'Noa Brekalo', 4, 1, '12305263', 2.89, 5),
(13, U&'Maksim Brki\0107', 1, 1, '25103647', 2.53, 1),
(14, 'Ante Budimir', 3, 1, '21553601', 4.58, 3),
(15, 'Lara Buljan', 2, 2, '25139803', 2.94, 4),
(16, U&'Ana Buni\0107', 2, 2, '98563258', 4.92, 6),
(17, U&'Damir Ciganovi\0107', 1, 1, '58741236', 3.89, 2),
(18, 'Alen Cindro', 1, 1, '58569658', 4.27, 5),
(19, U&'Elena \010celan', 4, 2, '15423541', 4.51, 1),
(20, U&'Mateo \010cili\0107', 1, 1, '54028130', 3.34, 3),
(21, U&'Matej Dadi\0107', 2, 1, '02514874', 3.12, 4),
(22, U&'Nika Dori\0107', 4, 2, '25140524', 3.72, 6),
(23, U&'Elena Dra\017eeta', 4, 2, '23562140', 2.88, 2),
(24, 'Milan Dudan', 3, 1, '25879684', 4.93, 5),
(25, 'Viktor Erceg', 3, 1, '87584962', 2.71, 1),
(26, U&'Mihael Filipovi\0107', 2, 1, '48579658', 4.18, 3),
(27, U&'Jakov Grgi\0107', 2, 1, '58841206', 2.32, 4),
(28, 'Ivona Holjevac', 3, 2, '69351632', 3.76, 6),
(29, 'Vanja Horvat', 4, 2, '54710251', 4.54, 2),
(30, U&'Marko Ili\0107', 4, 1, '14205184', 3.83, 5),
(31, U&'Nikola Ivani\0161evi\0107', 3, 1, '16245978', 4.44, 1),
(32, U&'Matija Jakeli\0107', 4, 1, '32615497', 4.66, 3),
(33, U&'Lana Jeleni\0107', 4, 2, '03164978', 2.95, 4),
(34, 'Ivana Karamarko', 1, 2, '15490316', 4.43, 6),
(35, U&'Dunja Kova\010d', 1, 2, '52621084', 2.47, 2),
(36, 'Jan Krajina', 3, 1, '15483966', 4.70, 5),
(37, 'Karlo Krizman', 2, 1, '54896321', 3.55, 1),
(38, 'Karla Leko', 2, 2, '16485123', 4.78, 3),
(39, 'Ivan Leko', 1, 1, '54720165', 4.40, 4),
(40, 'Filip Lucari', 4, 1, '15264826', 3.02, 6),
(41, U&'Nina Ljubi\010di\0107', 4, 2, '19420837', 2.31, 2),
(42, U&'Andrej Milo\0161', 1, 1, '98326598', 2.88, 5),
(43, 'Andrea Mlakar', 3, 2, '65218730', 2.52, 1),
(44, 'Martina Mornar', 4, 2, '54281547', 4.48, 3),
(45, 'Damjan Novak', 3, 1, '21879301', 4.27, 4),
(46, U&'Petar Peri\0107', 2, 1, '28173902', 4.33, 6),
(47, 'Helena Ratkaj', 2, 2, '28793601', 3.50, 2),
(48, 'Katarina Slunjski', 1, 2, '22178396', 3.40, 5),
(49, 'Juraj Skoko', 1, 1, '01280178', 4.94, 1),
(50, U&'Ian Zidari\0107', 1, 1, '21038547', 3.59, 3),
(51, U&'Roko \0110urinec', 1, 1, '25847369', 3.18, 4),
(52, U&'Jan Krivi\0107', 3, 1, '01427585', 4.60, 6),
(53, U&'Fran Katan\010di\0107', 2, 1, '32608520', 2.95, 2),
(54, U&'Stela Andri\0107', 4, 2, '36251652', 3.75, 5),
(55, U&'Josipa Lu\010di\0107', 1, 2, '25052858', 4.44, 1),
(56, 'Franka Benac', 3, 2, '77849630', 2.83, 3),
(57, 'Vjekoslav Vulin', 2, 1, '48579247', 2.99, 4),
(58, U&'Sandra Frankovi\0107', 4, 2, '15926034', 4.37, 6),
(59, 'Hrvoslav Lopata', 1, 1, '18459263', 4.14, 2),
(60, U&'Robert \010culjak', 3, 1, '18467301', 2.90, 5),
(61, U&'Ksenija Bozani\0107', 2, 2, '98690014', 2.83, 1),
(62, U&'Jakov Buni\0107', 4, 1, '87928748', 2.88, 3),
(63, U&'Kristina \010culina', 1, 2, '34608364', 4.75, 4),
(64, U&'Iva Mr\0161o', 3, 2, '14695589', 4.24, 6),
(65, 'Vesna Penava', 2, 2, '12301236', 2.77, 2),
(66, U&'Franjo Musi\0107', 4, 1, '45685915', 4.93, 5),
(67, U&'Zvonimir \0106esi\0107', 1, 1, '51785474', 4.24, 1),
(68, U&'Bruno Josipovi\0107', 3, 1, '36294878', 4.59, 3),
(69, U&'Toma Zeti\0107', 2, 1, '15260315', 3.02, 4),
(70, U&'Milan Ivo\0161', 4, 1, '28920146', 2.86, 6),
(71, 'Tomislav Baljak', 1, 1, '98120347', 3.15, 2),
(72, U&'Mirna Dori\0107', 3, 2, '69710626', 4.14, 5),
(73, 'Kristijan Dodig', 2, 1, '23641434', 3.33, 1),
(74, 'Gabrijel Majcen', 4, 1, '38738717', 3.68, 3);              
INSERT INTO "PUBLIC"."STUDENTI"("ID", "IME", "GODINA_STUDIJA", "SPOL_ID", "JMBAG", "PROSJEK_OCJENA", "FAKULTET_ID") OVERRIDING SYSTEM VALUE VALUES
(75, 'Stipe Butula', 1, 1, '56768595', 4.02, 4),
(97, 'Mario Torbarina', 4, 1, '36912787', 4.15, 2),
(193, U&'Talia Mi\0161kulin', 4, 2, '00625894', 5.00, 1);          
CREATE CACHED TABLE "PUBLIC"."KLUBOVI_STUDENATA"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "NAZIV" CHARACTER VARYING(50) NOT NULL,
    "FAKULTET_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL
);
ALTER TABLE "PUBLIC"."KLUBOVI_STUDENATA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("ID");            
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.KLUBOVI_STUDENATA;       
INSERT INTO "PUBLIC"."KLUBOVI_STUDENATA"("ID", "NAZIV", "FAKULTET_ID", "GRAD_ID") OVERRIDING SYSTEM VALUE VALUES
(1, 'Klub studenata za umjetnu inteligenciju TVZ', 2, 1),
(2, 'Klub studenata za umjetnu inteligenciju FER', 1, 1),
(3, 'Klub studenata za umjetnu inteligenciju FOI', 3, 3),
(4, 'Klub studenata za umjetnu inteligenciju ETF', 5, 4),
(5, 'Klub studenata za umjetnu inteligenciju FESB', 6, 2);      
CREATE CACHED TABLE "PUBLIC"."STUDENTI_PO_KLUBOVIMA"(
    "KLUB_STUDENATA_ID" INTEGER NOT NULL,
    "STUDENT_ID" INTEGER NOT NULL
);       
ALTER TABLE "PUBLIC"."STUDENTI_PO_KLUBOVIMA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C" PRIMARY KEY("KLUB_STUDENATA_ID", "STUDENT_ID");           
-- 77 +/- SELECT COUNT(*) FROM PUBLIC.STUDENTI_PO_KLUBOVIMA;  
INSERT INTO "PUBLIC"."STUDENTI_PO_KLUBOVIMA" VALUES
(1, 3),
(1, 5),
(1, 11),
(1, 17),
(1, 21),
(1, 23),
(1, 29),
(1, 35),
(1, 41),
(1, 47),
(1, 51),
(1, 53),
(1, 59),
(1, 65),
(1, 71),
(2, 1),
(2, 7),
(2, 13),
(2, 19),
(2, 25),
(2, 27),
(2, 31),
(2, 37),
(2, 43),
(2, 49),
(2, 55),
(2, 57),
(2, 61),
(2, 67),
(2, 73),
(3, 2),
(3, 8),
(3, 14),
(3, 20),
(3, 26),
(3, 32),
(3, 33),
(3, 38),
(3, 44),
(3, 50),
(3, 56),
(3, 62),
(3, 63),
(3, 68),
(3, 74),
(4, 6),
(4, 9),
(4, 12),
(4, 18),
(4, 24),
(4, 30),
(4, 36),
(4, 39),
(4, 42),
(4, 48),
(4, 54),
(4, 60),
(4, 66),
(4, 69),
(4, 72),
(5, 4),
(5, 10),
(5, 15),
(5, 16),
(5, 22),
(5, 28),
(5, 34),
(5, 40),
(5, 45),
(5, 46),
(5, 52),
(5, 58),
(5, 64),
(5, 70),
(5, 75),
(2, 193),
(1, 97); 
CREATE CACHED TABLE "PUBLIC"."KVIZOVI"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 97) NOT NULL,
    "NAZIV" CHARACTER VARYING(30) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "NAGRADA_EUR" INTEGER NOT NULL
);   
ALTER TABLE "PUBLIC"."KVIZOVI" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID");      
-- 6 +/- SELECT COUNT(*) FROM PUBLIC.KVIZOVI; 
INSERT INTO "PUBLIC"."KVIZOVI"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "NAGRADA_EUR") OVERRIDING SYSTEM VALUE VALUES
(1, U&'Kviz op\0107eg znanja', 2, 1, DATE '2024-01-01', DATE '2024-01-01', 100),
(2, 'Kviz znanja AI', 5, 2, DATE '2024-01-05', DATE '2024-01-05', 50),
(3, 'Kviz TVZ - SPORT', 1, 1, DATE '2023-01-15', DATE '2023-01-15', 150),
(4, 'Kviz TVZ - GLAZBA', 1, 1, DATE '2024-02-15', DATE '2024-02-15', 150),
(5, 'Kviz TVZ - FILMOVI', 1, 1, DATE '2024-03-15', DATE '2024-03-15', 150),
(6, U&'Studentski kviz Vara\017edin', 3, 3, DATE '2024-04-20', DATE '2024-04-20', 100);      
CREATE CACHED TABLE "PUBLIC"."NATJECANJA"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 129) NOT NULL,
    "NAZIV" CHARACTER VARYING(30) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "TEMA_NATJECANJA" CHARACTER VARYING(100) NOT NULL,
    "NAGRADA_EUR" INTEGER NOT NULL
);       
ALTER TABLE "PUBLIC"."NATJECANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");   
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.NATJECANJA;              
INSERT INTO "PUBLIC"."NATJECANJA"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "TEMA_NATJECANJA", "NAGRADA_EUR") OVERRIDING SYSTEM VALUE VALUES
(1, 'Primjena umjetne inteligencije', 4, 4, DATE '2024-01-15', DATE '2024-01-15', 'Primjena umjetne inteligencije', 300),
(2, 'AI BattleGround', 5, 2, DATE '2024-01-20', DATE '2024-01-21', 'Programiranje umjetne inteligencije', 1500),
(3, 'Progressive minds', 2, 1, DATE '2024-02-20', DATE '2024-02-21', 'Popularizacija umjetne inteligencije', 500),
(99, 'Project Future', 1, 1, DATE '2024-04-05', DATE '2024-04-10', U&'Osmi\0161ljavanje i implementiranje kreativnih i inovativnih AI rje\0161enja', 2000);         
CREATE CACHED TABLE "PUBLIC"."PREDAVANJA"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 97) NOT NULL,
    "NAZIV" CHARACTER VARYING(30) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "TEMA_PREDAVANJA" CHARACTER VARYING(50) NOT NULL
);              
ALTER TABLE "PUBLIC"."PREDAVANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_5" PRIMARY KEY("ID");   
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.PREDAVANJA;              
INSERT INTO "PUBLIC"."PREDAVANJA"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "TEMA_PREDAVANJA") OVERRIDING SYSTEM VALUE VALUES
(1, U&'Metode dubokog u\010denja', 3, 3, DATE '2024-01-31', DATE '2024-01-31', U&'Duboko u\010denje'),
(2, 'Napredak umjetne inteligencije', 4, 4, DATE '2024-02-16', DATE '2024-02-16', U&'Dosada\0161nji i daljnji razvoj umjetne inteligencije'),
(3, 'Dangers of AI', 1, 1, DATE '2024-03-06', DATE '2024-03-06', U&'Opasnosti i odr\017eivi razvoj umjetne inteligencije'),
(4, 'Primjena umjetne inteligencije', 5, 2, DATE '2024-04-17', DATE '2024-04-17', 'Primjena umjetne inteligencije');            
CREATE CACHED TABLE "PUBLIC"."PROJEKTI"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 65) NOT NULL,
    "NAZIV" CHARACTER VARYING(20) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "TEMA_PROJEKTA" CHARACTER VARYING(100) NOT NULL
); 
ALTER TABLE "PUBLIC"."PROJEKTI" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID");     
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.PROJEKTI;
INSERT INTO "PUBLIC"."PROJEKTI"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "TEMA_PROJEKTA") OVERRIDING SYSTEM VALUE VALUES
(1, 'Project Inovation', 3, 3, DATE '2024-05-03', DATE '2024-05-06', 'Izrada sustava za prepoznavanje teksta kojeg je generirao Chat-GPT'),
(2, 'Project brAIn', 5, 2, DATE '2024-03-13', DATE '2024-04-13', U&'Osmi\0161ljavanje, izrada i prezentacija projekta na temu umjetne inteligencije'),
(3, 'ETF-AI', 4, 4, DATE '2024-10-10', DATE '2024-12-10', U&'Izrada sustava za obradu podataka o kretanju vrijednosti dionica te predikciju budu\0107ih kretanja'),
(4, 'Mc2', 1, 1, DATE '2024-02-07', DATE '2024-06-19', U&'Izrada, dizajn i marketing mobilnih, web i IoT rje\0161enja');      
CREATE CACHED TABLE "PUBLIC"."PUTOVANJA"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 65) NOT NULL,
    "NAZIV" CHARACTER VARYING(30) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "POVOD_PUTOVANJA" CHARACTER VARYING(100) NOT NULL
);              
ALTER TABLE "PUBLIC"."PUTOVANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("ID");    
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.PUTOVANJA;               
INSERT INTO "PUBLIC"."PUTOVANJA"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "POVOD_PUTOVANJA") OVERRIDING SYSTEM VALUE VALUES
(1, U&'Motivacijski vikend - Vara\017edin', 4, 3, DATE '2024-04-13', DATE '2024-04-15', 'Motivacijski vikend'),
(2, 'Team building', 2, 4, DATE '2024-06-15', DATE '2024-06-18', 'Team building'),
(3, 'Edukacijski program - Osijek', 3, 5, DATE '2024-02-17', DATE '2024-02-17', U&'Odr\017eavanje edukacijskih programa na javnim mjestima u svrhu popularizacije umjetne inteligencije'),
(4, 'Edukacijski program - Zagreb', 3, 1, DATE '2024-03-17', DATE '2024-03-17', U&'Odr\017eavanje edukacijskih programa na javnim mjestima u svrhu popularizacije umjetne inteligencije'),
(5, 'Edukacijski program - Split', 3, 2, DATE '2024-04-17', DATE '2024-04-17', U&'Odr\017eavanje edukacijskih programa na javnim mjestima u svrhu popularizacije umjetne inteligencije'),
(6, U&'Edukacijski program - Vara\017edin', 3, 3, DATE '2024-05-17', DATE '2024-05-17', U&'Odr\017eavanje edukacijskih programa na javnim mjestima u svrhu popularizacije umjetne inteligencije'),
(7, 'Motivacijski vikend - Osijek', 2, 5, DATE '2024-05-05', DATE '2024-05-07', 'Motivacijski vikend');  
CREATE CACHED TABLE "PUBLIC"."ZABAVE"(
    "ID" INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1 RESTART WITH 65) NOT NULL,
    "NAZIV" CHARACTER VARYING(20) NOT NULL,
    "KLUB_ORGANIZATOR_ID" INTEGER NOT NULL,
    "GRAD_ID" INTEGER NOT NULL,
    "DATUM_POCETKA" DATE NOT NULL,
    "DATUM_ZAVRSETKA" DATE NOT NULL,
    "NAZIV_LOKALA" CHARACTER VARYING(20) NOT NULL
);     
ALTER TABLE "PUBLIC"."ZABAVE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9D" PRIMARY KEY("ID");      
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.ZABAVE;  
INSERT INTO "PUBLIC"."ZABAVE"("ID", "NAZIV", "KLUB_ORGANIZATOR_ID", "GRAD_ID", "DATUM_POCETKA", "DATUM_ZAVRSETKA", "NAZIV_LOKALA") OVERRIDING SYSTEM VALUE VALUES
(1, U&'Bruco\0161ijada - TVZ', 1, 1, DATE '2023-10-24', DATE '2023-10-24', 'Klub Roko'),
(2, U&'Bruco\0161ijada - FER', 2, 1, DATE '2023-10-25', DATE '2023-10-25', 'Kset'),
(3, U&'Ro\0111endan kluba FOI', 3, 3, DATE '2024-03-07', DATE '2024-03-07', U&'No\0107ni klub Bistro'),
(4, 'Zabava - ETF', 4, 4, DATE '2024-02-29', DATE '2024-02-29', 'Hemingway Bar'),
(5, U&'Bruco\0161ijada - FESB', 5, 2, DATE '2023-10-31', DATE '2023-10-31', 'Klub Kocka');      
ALTER TABLE "PUBLIC"."PROJEKTI" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F3" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;             
ALTER TABLE "PUBLIC"."NATJECANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_ED" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."PUTOVANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_BA" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."PREDAVANJA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_55" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."STUDENTI_PO_KLUBOVIMA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C3E" FOREIGN KEY("STUDENT_ID") REFERENCES "PUBLIC"."STUDENTI"("ID") NOCHECK; 
ALTER TABLE "PUBLIC"."KVIZOVI" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_16" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;              
ALTER TABLE "PUBLIC"."ZABAVE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9D4" FOREIGN KEY("KLUB_ORGANIZATOR_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;              
ALTER TABLE "PUBLIC"."STUDENTI_PO_KLUBOVIMA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C3" FOREIGN KEY("KLUB_STUDENATA_ID") REFERENCES "PUBLIC"."KLUBOVI_STUDENATA"("ID") NOCHECK;  
