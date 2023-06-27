UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`connection`
-- -----------------------------------------------------
LOCK TABLES connection WRITE;

INSERT INTO connection (email, password, PMB_account_id)
VALUES 
('user1_test@gmail.com', 'pwd1', 1),
('user2_test@gmail.com', 'pwd2', 2),
('user3_test@gmail.com', 'pwd3', 3),
('user4_test@gmail.com', 'pwd4', 4),
('user5_test@gmail.com', 'pwd5', 5),
('user6_test@gmail.com', 'pwd6', 6),
('user7_test@gmail.com', 'pwd7', 7),
('user8_test@gmail.com', 'pwd8', 8),
('user9_test@gmail.com', 'pwd9', 9);

UNLOCK TABLES;



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

INSERT INTO PMB_account (balance)
VALUES  (100), (200), (300), (400), (500), (600), (700), (800), (900);

UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `paymybuddy_db_test`.`transaction`
-- -----------------------------------------------------
LOCK TABLES transaction WRITE;

INSERT INTO transaction(amount, description, pmb_account_id, pmb_account_id1)
VALUES 
(20, 'Transaction user1 vers user2', 1, 2),
(30, 'Transaction user1 vers user3', 1, 3),

(30, 'Transaction user2 vers user3', 2, 3),
(40, 'Transaction user2 vers user4', 2, 4),

(40, 'Transaction user3 vers user4', 3, 4),
(50, 'Transaction user3 vers user5', 3, 5),

(50, 'Transaction user4 vers user5', 4, 5),
(60, 'Transaction user4 vers user6', 4, 6),

(60, 'Transaction user5 vers user6', 5, 6),
(70, 'Transaction user5 vers user7', 5, 7),

(70, 'Transaction user6 vers user7', 6, 7),
(80, 'Transaction user6 vers user8', 6, 8),

(80, 'Transaction user7 vers user8', 7, 8),
(90, 'Transaction user7 vers user9', 7, 9),

(90, 'Transaction user8 vers user9', 8, 9),

(10, 'Transaction user9 vers user1', 9, 1),
(20, 'Transaction user9 vers user2', 9, 2),
(30, 'Transaction user9 vers user3', 9, 3),
(40, 'Transaction user9 vers user4', 9, 4),
(50, 'Transaction user9 vers user5', 9, 5),
(60, 'Transaction user9 vers user6', 9, 6),
(70, 'Transaction user9 vers user7', 9, 7),
(80, 'Transaction user9 vers user8', 9, 8);

UNLOCK TABLES;

