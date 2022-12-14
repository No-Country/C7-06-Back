-- MySQL Script generated by MySQL Workbench
-- Fri Oct 14 15:31:29 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_animatch
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_animatch
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_animatch` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_animatch` ;

-- -----------------------------------------------------
-- Table `db_animatch`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`locations` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_animatch`.`pets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`pets` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `age` INT NOT NULL,
  `animal_type` VARCHAR(255) NULL DEFAULT NULL,
  `created_date` DATETIME(6) NULL DEFAULT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `gender` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `pure_race` BIT(1) NOT NULL,
  `race` VARCHAR(255) NULL DEFAULT NULL,
  `size` VARCHAR(255) NULL DEFAULT NULL,
  `updated_date` DATETIME(6) NULL DEFAULT NULL,
  `vaccinations_up_to_date` BIT(1) NOT NULL,
  `weight` DOUBLE NOT NULL,
  `location_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKdt2oerri32m1yhmbiqa66aiqc` (`location_id` ASC) VISIBLE,
  INDEX `FKc47kjb41qf50bwgddm024m5xn` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKc47kjb41qf50bwgddm024m5xn`
    FOREIGN KEY (`user_id`)
    REFERENCES `db_animatch`.`users` (`id`),
  CONSTRAINT `FKdt2oerri32m1yhmbiqa66aiqc`
    FOREIGN KEY (`location_id`)
    REFERENCES `db_animatch`.`locations` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_animatch`.`pictures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`pictures` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `key_number` VARCHAR(255) NULL DEFAULT NULL,
  `path` VARCHAR(255) NULL DEFAULT NULL,
  `pet_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKbvprv5hr8ey78rfkv3srhdp6g` (`pet_id` ASC) VISIBLE,
  CONSTRAINT `FKbvprv5hr8ey78rfkv3srhdp6g`
    FOREIGN KEY (`pet_id`)
    REFERENCES `db_animatch`.`pets` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_animatch`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `phone_number` VARCHAR(255) NULL DEFAULT NULL,
  `role` VARCHAR(255) NULL DEFAULT NULL,
  `surname` VARCHAR(255) NULL DEFAULT NULL,
  `picture_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7` (`email` ASC) VISIBLE,
  INDEX `FK16449k08lp0f50csh6jlb5g2c` (`picture_id` ASC) VISIBLE,
  CONSTRAINT `FK16449k08lp0f50csh6jlb5g2c`
    FOREIGN KEY (`picture_id`)
    REFERENCES `db_animatch`.`pictures` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_animatch`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`comments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_date` DATETIME(6) NULL DEFAULT NULL,
  `message` VARCHAR(500) NULL DEFAULT NULL,
  `updated_date` DATETIME(6) NULL DEFAULT NULL,
  `pet_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKlyu6qetbg4jx1524yuone1rrs` (`pet_id` ASC) VISIBLE,
  INDEX `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq`
    FOREIGN KEY (`user_id`)
    REFERENCES `db_animatch`.`users` (`id`),
  CONSTRAINT `FKlyu6qetbg4jx1524yuone1rrs`
    FOREIGN KEY (`pet_id`)
    REFERENCES `db_animatch`.`pets` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_animatch`.`favourites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_animatch`.`favourites` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `favourite` BIT(1) NOT NULL,
  `pet_id` BIGINT NULL DEFAULT NULL,
  `user_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKb7jteao4rbcdqm12ksm96w86e` (`pet_id` ASC) VISIBLE,
  INDEX `FK6ffqdnwmm3ks4fenx2ml1uap8` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK6ffqdnwmm3ks4fenx2ml1uap8`
    FOREIGN KEY (`user_id`)
    REFERENCES `db_animatch`.`users` (`id`),
  CONSTRAINT `FKb7jteao4rbcdqm12ksm96w86e`
    FOREIGN KEY (`pet_id`)
    REFERENCES `db_animatch`.`pets` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;