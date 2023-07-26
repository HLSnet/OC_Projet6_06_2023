-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy_db_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `paymybuddy_db_test` ;

-- -----------------------------------------------------
-- Schema paymybuddy_db_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy_db_test` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddy_db_test` ;

-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db_test`.`connection` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db_test`.`connection` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`PMB_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db_test`.`PMB_account` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db_test`.`PMB_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DOUBLE NOT NULL DEFAULT 0,
  `connection_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PMB_account_connection1_idx` (`connection_id` ASC) VISIBLE,
  CONSTRAINT `fk_PMB_account_connection1`
    FOREIGN KEY (`connection_id`)
    REFERENCES `paymybuddy_db_test`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db_test`.`transaction` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db_test`.`transaction` (
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
    REFERENCES `paymybuddy_db_test`.`PMB_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PMB_account_has_PMB_account_PMB_account1`
    FOREIGN KEY (`PMB_account_id1`)
    REFERENCES `paymybuddy_db_test`.`PMB_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`connection_buddies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy_db_test`.`connection_buddies` ;

CREATE TABLE IF NOT EXISTS `paymybuddy_db_test`.`connection_buddies` (
  `connection_id` INT NOT NULL,
  `connection_id1` INT NOT NULL,
  INDEX `fk_connection_has_connection_connection1_idx` (`connection_id` ASC) VISIBLE,
  INDEX `fk_connection_has_connection_connection2_idx` (`connection_id1` ASC) VISIBLE,
  PRIMARY KEY (`connection_id`, `connection_id1`),
  CONSTRAINT `fk_connection_has_connection_connection1`
    FOREIGN KEY (`connection_id`)
    REFERENCES `paymybuddy_db_test`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_connection_has_connection_connection2`
    FOREIGN KEY (`connection_id1`)
    REFERENCES `paymybuddy_db_test`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;





UNLOCK TABLES;

USE `paymybuddy_db_test` ;
-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`connection`
-- -----------------------------------------------------
LOCK TABLES connection WRITE;

INSERT INTO connection (email, password, name)
VALUES 
('connection1_test@gmail.com', 'pwd1', 'buddy1'),
('connection2_test@gmail.com', 'pwd2', 'buddy2'),
('connection3_test@gmail.com', 'pwd3', 'buddy3'),
('connection4_test@gmail.com', 'pwd4', 'buddy4'),
('connection5_test@gmail.com', 'pwd5', 'buddy5'),
('connection6_test@gmail.com', 'pwd6', 'buddy6'),
('connection7_test@gmail.com', 'pwd7', 'buddy7'),
('connection8_test@gmail.com', 'pwd8', 'buddy8'),
('connection9_test@gmail.com', 'pwd9', 'buddy9');

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`connection_buddies`
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
-- Table `paymybuddy_db_test`.`pmb_account`
-- -----------------------------------------------------
LOCK TABLES pmb_account WRITE;

INSERT INTO PMB_account (balance, connection_id)
VALUES (100, 1), (200, 2), (300, 3), (400, 4), (500, 5), (600, 6), (700, 7), (800, 8), (900, 9);

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`transaction`
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


