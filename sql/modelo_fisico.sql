-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema biblioteca
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema biblioteca
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `biblioteca` DEFAULT CHARACTER SET utf8 ;
USE `biblioteca` ;

-- -----------------------------------------------------
-- Table `biblioteca`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Usuario` (
  `CpfUsuario` VARCHAR(11) NOT NULL,
  `Nome` VARCHAR(60) NOT NULL,
  `Email` VARCHAR(60) NOT NULL,
  `Telefone` VARCHAR(11) NOT NULL,
  `DataNasc` DATE NOT NULL,
  `Endereco` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`CpfUsuario`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  UNIQUE INDEX `Cpf_UNIQUE` (`CpfUsuario` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Autor` (
  `CodAutor` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CodAutor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Categoria` (
  `CodCat` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`CodCat`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Editora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Editora` (
  `CodEditora` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`CodEditora`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Livro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Livro` (
  `CodLivro` INT NOT NULL AUTO_INCREMENT,
  `Titulo` VARCHAR(80) NOT NULL,
  `Isbn` VARCHAR(11) NOT NULL,
  `AnoPub` YEAR NOT NULL,
  `CodCat` INT NOT NULL,
  `CodEditora` INT NOT NULL,
  PRIMARY KEY (`CodLivro`),
  CONSTRAINT `fk_Livro_Categoria1`
    FOREIGN KEY (`CodCat`)
    REFERENCES `biblioteca`.`Categoria` (`CodCat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_Editora1`
    FOREIGN KEY (`CodEditora`)
    REFERENCES `biblioteca`.`Editora` (`CodEditora`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Escrito_por`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Escrito_por` (
  `CodLivro` INT NOT NULL,
  `CodAutor` INT NOT NULL,
  PRIMARY KEY (`CodLivro`, `CodAutor`),
  CONSTRAINT `fk_Escrito_Livro1`
    FOREIGN KEY (`CodLivro`)
    REFERENCES `biblioteca`.`Livro` (`CodLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Escrito_Autor1`
    FOREIGN KEY (`CodAutor`)
    REFERENCES `biblioteca`.`Autor` (`CodAutor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Emprestimo` (
  `CodEmp` INT NOT NULL AUTO_INCREMENT,
  `DataEmp` DATE NOT NULL,
  `DataDev` DATE GENERATED ALWAYS AS (DATE_ADD(DataEmp, INTERVAL 7 DAY)) VIRTUAL,
  `Extensoes` INT(3) NOT NULL DEFAULT 0,
  `Multa` DECIMAL(4,2) NULL,
  `CodLivro` INT NOT NULL,
  `CpfUsuario` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`CodEmp`),
  CONSTRAINT `fk_Emprestimo_Livro1`
    FOREIGN KEY (`CodLivro`)
    REFERENCES `biblioteca`.`Livro` (`CodLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Emprestimo_Usuario1`
    FOREIGN KEY (`CpfUsuario`)
    REFERENCES `biblioteca`.`Usuario` (`CpfUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `biblioteca`.`Reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`Reserva` (
  `CodReserva` INT NOT NULL AUTO_INCREMENT,
  `DataReserva` DATE NOT NULL,
  `DataVal` DATE GENERATED ALWAYS AS (DATE_ADD(reserva.DataReserva, INTERVAL 2 DAY)) VIRTUAL,
  `CodLivro` INT NOT NULL,
  `CpfUsuario` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`CodReserva`),
  CONSTRAINT `fk_Reserva_Livro1`
    FOREIGN KEY (`CodLivro`)
    REFERENCES `biblioteca`.`Livro` (`CodLivro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Usuario1`
    FOREIGN KEY (`CpfUsuario`)
    REFERENCES `biblioteca`.`Usuario` (`CpfUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `biblioteca` ;

-- -----------------------------------------------------
-- Placeholder table for view `biblioteca`.`listaLivro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biblioteca`.`listaLivro` (`CodLivro` INT, `Titulo` INT, `autores` INT, `Isbn` INT, `AnoPub` INT, `Categoria` INT, `Editora` INT);

-- -----------------------------------------------------
-- View `biblioteca`.`listaLivro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `biblioteca`.`listaLivro`;
USE `biblioteca`;
CREATE  OR REPLACE VIEW `listaLivro` AS
SELECT livro.CodLivro, livro.Titulo, GROUP_CONCAT(autor.Nome SEPARATOR ', ') AS autores, livro.Isbn, livro.AnoPub, categoria.Nome AS Categoria, editora.Nome AS Editora 
FROM livro 
JOIN escrito_por ON livro.CodLivro = escrito_por.CodLivro 
JOIN autor ON escrito_por.CodAutor = autor.CodAutor 
JOIN categoria ON livro.CodCat = categoria.CodCat
JOIN editora ON livro.CodEditora = editora.CodEditora
GROUP BY livro.CodLivro, livro.Titulo;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
