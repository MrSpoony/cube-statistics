SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS cube_db;
CREATE SCHEMA IF NOT EXISTS cube_db DEFAULT CHARACTER SET utf8;
USE cube_db;

DROP TABLE IF EXISTS cube_db.`event`;
CREATE TABLE IF NOT EXISTS cube_db.`event`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) NOT NULL,
    `wcalegal` TINYINT     NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`country`;
CREATE TABLE IF NOT EXISTS cube_db.`country`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`manufacture`;
CREATE TABLE IF NOT EXISTS cube_db.`manufacture`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NOT NULL,
    `country_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_manufacture_country1_idx` (`country_id` ASC) VISIBLE,
    CONSTRAINT `fk_manufacture_country1`
        FOREIGN KEY (`country_id`)
            REFERENCES cube_db.`country` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`cube`;
CREATE TABLE IF NOT EXISTS cube_db.`cube`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(100) NOT NULL,
    `event_id`       INT          NOT NULL,
    `manufacture_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_cube_event1_idx` (`event_id` ASC) VISIBLE,
    INDEX `fk_cube_manufacture1_idx` (`manufacture_id` ASC) VISIBLE,
    CONSTRAINT `fk_cube_event1`
        FOREIGN KEY (`event_id`)
            REFERENCES cube_db.`event` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_cube_manufacture1`
        FOREIGN KEY (`manufacture_id`)
            REFERENCES cube_db.`manufacture` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`cuber`;
CREATE TABLE IF NOT EXISTS cube_db.`cuber`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `firstname`  VARCHAR(100) NULL,
    `lastname`   VARCHAR(100) NULL,
    `birthdate`  DATE         NOT NULL,
    `country_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_cuber_country1_idx` (`country_id` ASC) VISIBLE,
    CONSTRAINT `fk_cuber_country1`
        FOREIGN KEY (`country_id`)
            REFERENCES cube_db.`country` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`town`;
CREATE TABLE IF NOT EXISTS cube_db.`town`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(100) NOT NULL,
    `postcode`   INT          NOT NULL,
    `country_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_town_country1_idx` (`country_id` ASC) VISIBLE,
    CONSTRAINT `fk_town_country1`
        FOREIGN KEY (`country_id`)
            REFERENCES cube_db.`country` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`location`;
CREATE TABLE IF NOT EXISTS cube_db.`location`
(
    `id`       INT          NOT NULL AUTO_INCREMENT,
    `location` VARCHAR(255) NOT NULL,
    `town_id`  INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_location_town1_idx` (`town_id` ASC) VISIBLE,
    CONSTRAINT `fk_location_town1`
        FOREIGN KEY (`town_id`)
            REFERENCES cube_db.`town` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`competition`;
CREATE TABLE IF NOT EXISTS cube_db.`competition`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(100) NOT NULL,
    `date`        DATE         NOT NULL,
    `location_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_competition_location1_idx` (`location_id` ASC) VISIBLE,
    CONSTRAINT `fk_competition_location1`
        FOREIGN KEY (`location_id`)
            REFERENCES cube_db.`location` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`time`;
CREATE TABLE IF NOT EXISTS cube_db.`time`
(
    `id`             INT   NOT NULL AUTO_INCREMENT,
    `time`           FLOAT NULL,
    `cuber_id`       INT   NOT NULL,
    `competition_id` INT   NOT NULL,
    `cube_id`        INT   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_time_cuber_idx` (`cuber_id` ASC) VISIBLE,
    INDEX `fk_time_competition1_idx` (`competition_id` ASC) VISIBLE,
    CONSTRAINT `fk_time_cuber`
        FOREIGN KEY (`cuber_id`)
            REFERENCES cube_db.`cuber` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_time_competition1`
        FOREIGN KEY (`competition_id`)
            REFERENCES cube_db.`competition` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_time_cube`
        FOREIGN KEY (`cube_id`)
            REFERENCES cube_db.`cube` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`cuber_has_cube`;
CREATE TABLE IF NOT EXISTS cube_db.`cuber_has_cube`
(
    `id`       INT     NOT NULL AUTO_INCREMENT,
    `cube_id`  INT     NOT NULL,
    `cuber_id` INT     NOT NULL,
    `maincube` TINYINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_cube_has_cuber_cuber1_idx` (`cuber_id` ASC) VISIBLE,
    INDEX `fk_cube_has_cuber_cube1_idx` (`cube_id` ASC) VISIBLE,
    CONSTRAINT `fk_cube_has_cuber_cube1`
        FOREIGN KEY (`cube_id`)
            REFERENCES cube_db.`cube` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_cube_has_cuber_cuber1`
        FOREIGN KEY (`cuber_id`)
            REFERENCES cube_db.`cuber` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


DROP TABLE IF EXISTS cube_db.`cuber_has_mainevent`;
CREATE TABLE IF NOT EXISTS cube_db.`cuber_has_mainevent`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `event_id` INT NOT NULL,
    `cuber_id` INT NOT NULL,
    INDEX `fk_event_has_cuber_cuber1_idx` (`cuber_id` ASC) VISIBLE,
    INDEX `fk_event_has_cuber_event1_idx` (`event_id` ASC) VISIBLE,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_event_has_cuber_event1`
        FOREIGN KEY (`event_id`)
            REFERENCES cube_db.`event` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_event_has_cuber_cuber1`
        FOREIGN KEY (`cuber_id`)
            REFERENCES cube_db.`cuber` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

DROP USER IF EXISTS 'cubeuser'@`localhost`;
FLUSH PRIVILEGES;
CREATE USER 'cubeuser'@`localhost` IDENTIFIED BY 'cubeuserpassword';
GRANT ALL ON cube_db.* TO 'cubeuser'@`localhost`;


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
       ('GAN', 1)
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
       ('Yoooo Cube', 1, 1)
;
COMMIT;

START TRANSACTION;
INSERT INTO cube_db.cuber(firstname, lastname, birthdate, country_id)
VALUES ('Kimi', 'Löffel', '2006-04-07', 2),
       ('Felikz', 'Zemdegs', '1995-12-20', 3)
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
       (3, 1, true)
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
INSERT INTO cube_db.time (time, cuber_id, competition_id)
VALUES (12.11, 1, 1),
       (null, 1, 1),
       (19.84, 1, 1),
       (13.87, 1, 1),
       (17.42, 1, 1)
;
COMMIT;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;