-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `paymybuddy_db` ;

-- -----------------------------------------------------
-- Schema paymybuddy_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy_db` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddy_db` ;

-- -----------------------------------------------------
-- Table `paymybuddy_db`.`connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db`.`connection` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db`.`connection` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`PMB_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db`.`PMB_account` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db`.`PMB_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DOUBLE NOT NULL DEFAULT 0,
  `connection_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PMB_account_connection1_idx` (`connection_id` ASC) VISIBLE,
  CONSTRAINT `fk_PMB_account_connection1`
    FOREIGN KEY (`connection_id`)
    REFERENCES `paymybuddy_db`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db`.`transaction` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL DEFAULT 0,
  `description` VARCHAR(45) NULL,
  `PMB_account_id` INT NOT NULL,
  `PMB_account_id1` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PMB_account_has_PMB_account_PMB_account1_idx` (`PMB_account_id1` ASC) VISIBLE,
  INDEX `fk_PMB_account_has_PMB_account_PMB_account_idx` (`PMB_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_PMB_account_has_PMB_account_PMB_account`
    FOREIGN KEY (`PMB_account_id`)
    REFERENCES `paymybuddy_db`.`PMB_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PMB_account_has_PMB_account_PMB_account1`
    FOREIGN KEY (`PMB_account_id1`)
    REFERENCES `paymybuddy_db`.`PMB_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`connection_buddies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db`.`connection_buddies` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db`.`connection_buddies` (
  `connection_id` INT NOT NULL,
  `connection_id1` INT NOT NULL,
  INDEX `fk_connection_has_connection_connection1_idx` (`connection_id` ASC) VISIBLE,
  INDEX `fk_connection_has_connection_connection2_idx` (`connection_id1` ASC) VISIBLE,
  PRIMARY KEY (`connection_id`, `connection_id1`),
  CONSTRAINT `fk_connection_has_connection_connection1`
    FOREIGN KEY (`connection_id`)
    REFERENCES `paymybuddy_db`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_connection_has_connection_connection2`
    FOREIGN KEY (`connection_id1`)
    REFERENCES `paymybuddy_db`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


UNLOCK TABLES;

USE `paymybuddy_db` ;
-- -----------------------------------------------------
-- Table `paymybuddy_db`.`connection`
-- -----------------------------------------------------
LOCK TABLES connection WRITE;

INSERT INTO connection (email, password, name)
VALUES 
('connection1@gmail.com', '$2a$10$jYqV13DfH/asr4XDITZBQODicR91gxC/2ZODSltVGuEp1B0EJISzm', 'buddy1'),
('connection2@gmail.com', '$2a$10$n1N9w5jajgRWEF95JRG9a.TyxIBoteGG0GiEc9cLpI4f.bAbUhQGm', 'buddy2'),
('connection3@gmail.com', '$2a$10$SGBvnuGeeBW7vyAv3XJAa.lC/e/NNFxB91BUAlKpAumJYC/eW9XSm', 'buddy3'),
('connection4@gmail.com', '$2a$10$Z8jBXmU4gx3ze/ukBgVehu7J62Y9RL4PtkmwmcVu4oAnidm47RIgC', 'buddy4'),
('connection5@gmail.com', '$2a$10$Q/znkhejmMg1pTZ0fdnxUeQ/iQTNcSgSh4kqOVO9f3DGAWKlEkkuy', 'buddy5'),
('connection6@gmail.com', '$2a$10$p.JIQWa6t3YyP5k4yYAnBetJDn80y5srHPv54KklCDhAF7MuAYyL2', 'buddy6'),
('connection7@gmail.com', '$2a$10$zLI1kvitlTxAuy5/NlEdIeuuzcTS.0mzdit9UxbtrrY1QWn3gq7C6', 'buddy7'),
('connection8@gmail.com', '$2a$10$xk6CHDJ7xEL.ryQaSL3w2edYRL9Hn4VAZpOrfrnPtVmibp6R6XEg6', 'buddy8'),
('connection9@gmail.com', '$2a$10$uKehyEJ7dHB3Uq5tmgg7YuIcEo/CLou2jZc0eXVI0bjJbm6Qhby5W', 'buddy9');

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`connection_buddies`
-- -----------------------------------------------------
LOCK TABLES connection_buddies WRITE;

INSERT INTO connection_buddies (connection_id, connection_id1)
VALUES 
(1, 2), (1, 3),(1, 4),
 
(2, 3), (2, 4), (2, 5),

(3, 4), (3, 5), (3, 6),

(4, 5), (4, 6), (4, 7),

(5, 6), (5, 7), (5, 8),

(6, 7), (6, 8), (6, 9),

(7, 8), (7, 9),

(8, 9),

(9, 1), (9, 2), (9, 3), (9, 4), (9, 5), (9, 6), (9, 7), (9, 8);

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`pmb_account`
-- -----------------------------------------------------
LOCK TABLES pmb_account WRITE;

INSERT INTO PMB_account (balance, connection_id)
VALUES (100, 1), (200, 2), (300, 3), (400, 4), (500, 5), (600, 6), (700, 7), (800, 8), (900, 9);

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db`.`transaction`
-- -----------------------------------------------------
LOCK TABLES transaction WRITE;

INSERT INTO transaction(amount, description, pmb_account_id, pmb_account_id1)
VALUES 
(20, 'Transaction PMB_account1 vers PMB_account2', 1, 2),
(30, 'Transaction PMB_account1 vers PMB_account3', 1, 3),

(30, 'Transaction PMB_account2 vers PMB_account3', 2, 3),
(40, 'Transaction PMB_account2 vers PMB_account4', 2, 4),

(40, 'Transaction PMB_account3 vers PMB_account4', 3, 4),
(50, 'Transaction PMB_account3 vers PMB_account5', 3, 5),

(50, 'Transaction PMB_account4 vers PMB_account5', 4, 5),
(60, 'Transaction PMB_account4 vers PMB_account6', 4, 6),

(60, 'Transaction PMB_account5 vers PMB_account6', 5, 6),
(70, 'Transaction PMB_account5 vers PMB_account7', 5, 7),

(70, 'Transaction PMB_account6 vers PMB_account7', 6, 7),
(80, 'Transaction PMB_account6 vers PMB_account8', 6, 8),

(80, 'Transaction PMB_account7 vers PMB_account8', 7, 8),
(90, 'Transaction PMB_account7 vers PMB_account9', 7, 9),

(90, 'Transaction PMB_account8 vers PMB_account9', 8, 9),

(10, 'Transaction PMB_account9 vers PMB_account1', 9, 1),
(20, 'Transaction PMB_account9 vers PMB_account2', 9, 2),
(30, 'Transaction PMB_account9 vers PMB_account3', 9, 3),
(40, 'Transaction PMB_account9 vers PMB_account4', 9, 4),
(50, 'Transaction PMB_account9 vers PMB_account5', 9, 5),
(60, 'Transaction PMB_account9 vers PMB_account6', 9, 6),
(70, 'Transaction PMB_account9 vers PMB_account7', 9, 7),
(80, 'Transaction PMB_account9 vers PMB_account8', 9, 8);

UNLOCK TABLES;

