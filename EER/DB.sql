-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema startcode_test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema startcode_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `startcode_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `startcode_test` ;

-- -----------------------------------------------------
-- Table `startcode_test`.`Car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`Car` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `brand` VARCHAR(255) NULL DEFAULT NULL,
    `color` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    `sponsor` VARCHAR(255) NULL DEFAULT NULL,
    `year` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`Race`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`Race` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `duration` VARCHAR(255) NULL DEFAULT NULL,
    `location` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    `startDate` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`Car_Race`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`Car_Race` (
                                                           `cars` INT NOT NULL,
                                                           `races` INT NOT NULL,
                                                           PRIMARY KEY (`cars`, `races`),
    INDEX `FK_Car_Race_races` (`races` ASC) VISIBLE,
    CONSTRAINT `FK_Car_Race_cars`
    FOREIGN KEY (`cars`)
    REFERENCES `startcode_test`.`Race` (`id`),
    CONSTRAINT `FK_Car_Race_races`
    FOREIGN KEY (`races`)
    REFERENCES `startcode_test`.`Car` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`Driver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`Driver` (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `birthYear` INT NULL DEFAULT NULL,
                                                         `experience` VARCHAR(255) NULL DEFAULT NULL,
    `gender` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    `car_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_Driver_car_id` (`car_id` ASC) VISIBLE,
    CONSTRAINT `FK_Driver_car_id`
    FOREIGN KEY (`car_id`)
    REFERENCES `startcode_test`.`Car` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`roles` (
    `role_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`role_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`users` (
    `user_name` VARCHAR(25) NOT NULL,
    `user_pass` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`user_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `startcode_test`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `startcode_test`.`user_roles` (
    `role_name` VARCHAR(20) NOT NULL,
    `user_name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`role_name`, `user_name`),
    INDEX `FK_user_roles_user_name` (`user_name` ASC) VISIBLE,
    CONSTRAINT `FK_user_roles_role_name`
    FOREIGN KEY (`role_name`)
    REFERENCES `startcode_test`.`roles` (`role_name`),
    CONSTRAINT `FK_user_roles_user_name`
    FOREIGN KEY (`user_name`)
    REFERENCES `startcode_test`.`users` (`user_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;