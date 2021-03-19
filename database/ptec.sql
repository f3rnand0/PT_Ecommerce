DROP DATABASE IF EXISTS `ptec`;
CREATE DATABASE IF NOT EXISTS `ptec`;
USE `ptec`;
-- MySQL dump 10.16  Distrib 10.1.47-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: ptec
-- ------------------------------------------------------
-- Server version	10.1.47-MariaDB-0ubuntu0.18.04.1

DROP TABLE IF EXISTS `product_photo`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `photo` longblob DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_1_idx` (`parent_id`),
  CONSTRAINT `fk_category_1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(500) NULL,
  `weight` decimal(10,2) NULL,
  `price` decimal(10,2) NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_1_idx` (`category_id`),
  CONSTRAINT `fk_product_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `photo` longblob NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_photo_1_idx` (`product_id`),
  CONSTRAINT `fk_product_photo_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
