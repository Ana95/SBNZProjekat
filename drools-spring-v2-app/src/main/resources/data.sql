--users
insert into user(username, password, name, surname, email, role) values('mare', 'mare123', 'Marko', 'Radovanović', 'mare@gmail.com', 0);
insert into user(username, password, name, surname, email, role) values('dare', 'dare123', 'Darko', 'Stevanović', 'dare@gmail.com', 1);
insert into user(username, password, name, surname, email, role) values('nina', 'nina123', 'Nikolina', 'Rudić', 'nina@gmail.com', 1);
insert into user(username, password, name, surname, email, role) values('neva', 'neva123', 'Nevena', 'Lazarević', 'neva@gmail.com', 1);

--illnesses
insert into illness(name, illness_type) values('Prehlada', 0);
insert into illness(name, illness_type) values('Groznica', 0);
insert into illness(name, illness_type) values('Upala krajnika', 0);
insert into illness(name, illness_type) values('Sinusna infekcija', 0);
insert into illness(name, illness_type) values('Hipertenzija', 1);
insert into illness(name, illness_type) values('Dijabetes', 1);
insert into illness(name, illness_type) values('Hronicna bubrezna bolest', 2);
insert into illness(name, illness_type) values('Akutna bubrezna povreda', 2);

--Prehlada
insert into symptom(term, helper, is_specific, illness) values(0, 'Curenje iz nosa', 0, 1);
insert into symptom(term, helper, is_specific, illness) values(1, 'Bol u grlu', 0, 1);
insert into symptom(term, helper, is_specific, illness) values(2, 'Glavobolja', 0, 1);
insert into symptom(term, helper, is_specific, illness) values(3, 'Kijanje', 0, 1);
insert into symptom(term, helper, is_specific, illness) values(4, 'Kašalj', 0, 1);

--Groznica
insert into symptom(term, helper, is_specific, illness) values(3, 'Kijanje', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(1, 'Bol u grlu', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(4, 'Kašalj', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(5, 'Temperatura veća od 38', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(0, 'Curenje iz nosa', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(2, 'Glavobolja', 0, 2);
insert into symptom(term, helper, is_specific, illness) values(6, 'Drhtavica', 0, 2);

--Upala krajnika
insert into symptom(term, helper, is_specific, illness) values(1, 'Bol u grlu', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(7, 'Bol koji se širi do ušiju', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(2, 'Glavobolja', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(8, 'Temperatura od 40 do 41', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(6, 'Drhtavica', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(9, 'Gubitak apetita', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(10, 'Umor', 0, 3);
insert into symptom(term, helper, is_specific, illness) values(11, 'Žuti sekret iz nosa', 0, 3);

--Sinusna infekcija
insert into symptom(term, helper, is_specific, illness) values(12, 'Oticanje oko očiju', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(2, 'Glavobolja', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(11, 'Žuti sekret iz nosa', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(1, 'Bol u grlu', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(5, 'Temperatura veća od 38', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(4, 'Kašalj', 0, 4);
insert into symptom(term, helper, is_specific, illness) values(25, 'Pacijent je bolovao od prehlade ili groznice u poslednjih 60 dana', 1, 4);

--Hipertenzija
insert into symptom(term, helper, is_specific, illness) values(26, 'U poslednjih 6 mjeseci zabilježeno barem 10 slučajeva u kojima je pacijent imao visok pritisak', 0, 5);

--Dijabetes
insert into symptom(term, helper, is_specific, illness) values(14, 'Često uriniranje', 0, 6);
insert into symptom(term, helper, is_specific, illness) values(15, 'Gubitak tjelesne težine', 0, 6);
insert into symptom(term, helper, is_specific, illness) values(16, 'Zamor', 0, 6);
insert into symptom(term, helper, is_specific, illness) values(17, 'Mučnina i povraćanje', 0, 6);

--Hronična bubrežna bolest
insert into symptom(term, helper, is_specific, illness) values(16, 'Zamor', 0, 7);
insert into symptom(term, helper, is_specific, illness) values(18, 'Nocturia', 0, 7);
insert into symptom(term, helper, is_specific, illness) values(19, 'Otoci nogu i zglobova', 0, 7);
insert into symptom(term, helper, is_specific, illness) values(20, 'Gušenje', 0, 7);
insert into symptom(term, helper, is_specific, illness) values(21, 'Bol u grudima', 0, 7);
insert into symptom(term, helper, is_specific, illness) values(27, 'Pacijent boluje od hipertenzije više od 6 mjeseci', 1, 7);
insert into symptom(term, helper, is_specific, illness) values(28, 'Pacijent boluje od dijabetesa', 1, 7);

--Akutna bubrežna povreda
insert into symptom(term, helper, is_specific, illness) values(24, 'Pacijent se oporavlja od operacije', 1, 8);
insert into symptom(term, helper, is_specific, illness) values(16, 'Zamor', 0, 8);
insert into symptom(term, helper, is_specific, illness) values(20, 'Gušenje', 0, 8);
insert into symptom(term, helper, is_specific, illness) values(19, 'Otoci nogu i zglobova', 0, 8);
insert into symptom(term, helper, is_specific, illness) values(22, 'Dijareja', 0, 8);
insert into symptom(term, helper, is_specific, illness) values(29, 'Pacijent bolovao od bolesti koja kao simptom ima povišenu temperaturu u poslednjih 14 dana', 1, 8);
insert into symptom(term, helper, is_specific, illness) values(30, 'U poslednjih 21 dan pacijentu je dijagnostifikovana bolest za koju je primao antibiotike', 1, 8);
