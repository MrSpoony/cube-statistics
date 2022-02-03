USE cube_db;

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE cube_db.time;
TRUNCATE TABLE cube_db.competition;
TRUNCATE TABLE cube_db.location;
TRUNCATE TABLE cube_db.town;
TRUNCATE TABLE cube_db.cuber_has_cube;
TRUNCATE TABLE cube_db.cuber_has_mainevent;
TRUNCATE TABLE cube_db.cuber;
TRUNCATE TABLE cube_db.cube;
TRUNCATE TABLE cube_db.event;
TRUNCATE TABLE cube_db.manufacture;
TRUNCATE TABLE cube_db.country;
SET FOREIGN_KEY_CHECKS=1;

START TRANSACTION;
INSERT INTO cube_db.country (name)
VALUES ('china'),
       ('switzerland'),
       ('australia')
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.manufacture (name, country_id)
VALUES ('MoYu', 1),
       ('GAN', 1),
       ('YuXin', 1)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.event (name, wcalegal)
VALUES ('3x3', true),
       ('OH', true),
       ('4x4', true)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.cube (name, event_id, manufacture_id)
VALUES ('RS3M 2020', 1, 1),
       ('11 M Pro', 1, 2),
       ('The Yoo Cube', 1, 1)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.cuber(firstname, lastname, birthdate, country_id)
VALUES ('Kimi', 'Löffel', '2006-04-07', 2),
       ('Feliks', 'Zemdegs', '1995-12-20', 3)
;
COMMIT;


START TRANSACTION;
INSERT INTO cube_db.cuber_has_mainevent(event_id, cuber_id)
VALUES (1, 1),
       (1, 2)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.cuber_has_cube (cube_id, cuber_id, maincube)
VALUES (1, 1, false),
       (2, 1, false),
       (3, 1, true),
       (1, 2, false),
       (2, 2, true)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.town (name, postcode, country_id)
VALUES ('Bern', 3000, 2),
       ('Schwyz', 6430, 2)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.location (location, town_id)
VALUES ('Kollegiumstrasse 24', 2)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.competition (name, date, location_id)
VALUES ('Offline Schwyz Sunday 2021', '2021-08-16', 1),
       ('Offline Schwyz Saturday 2021', '2021-08-15', 1)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.time (time, cuber_id, competition_id, cube_id)
VALUES (12.11, 1, 1, 3),
       (null, 1, 1, 3),
       (19.84, 1, 1, 3),
       (13.87, 1, 1, 3),
       (17.42, 1, 1, 3)
;
COMMIT;