CREATE DATABASE  IF NOT EXISTS `reasoner-engine` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `reasoner-engine`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: smartparking.ceb31lisbcnl.us-east-1.rds.amazonaws.com    Database: reasoner-engine
-- ------------------------------------------------------
-- Server version	5.6.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `excution`
--

DROP TABLE IF EXISTS `excution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `excution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `created` timestamp NULL,
  `updated` timestamp NULL,
  `sensor` varchar(300) NOT NULL,
  `ended` datetime DEFAULT NULL,
  `full_data` mediumtext,
  `inffered_data` mediumtext,
  `original_data` mediumtext,
  `rule_content` mediumtext,
  `started` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `register_rule_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlwptsxtkmqtv0c1gn99q2jilc` (`register_rule_id`),
  CONSTRAINT `FKlwptsxtkmqtv0c1gn99q2jilc` FOREIGN KEY (`register_rule_id`) REFERENCES `register_rule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reasoning`
--

DROP TABLE IF EXISTS `reasoning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reasoning` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `created` timestamp NULL,
  `updated` timestamp NULL,
  `content` mediumtext NOT NULL,
  `sensor` varchar(300) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `sensor_endp` varchar(300) DEFAULT NULL,
  `sensor_meta` mediumtext,
  `sensor_sample_data` mediumtext,
  `latitude` float DEFAULT '0',
  `longitude` float DEFAULT '0',
  `quantity_kind` varchar(255) DEFAULT NULL,
  `unit_of_measurement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `register_rule`
--

DROP TABLE IF EXISTS `register_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `register_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` mediumtext NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `full_data` mediumtext,
  `inferred_data` mediumtext,
  `name` varchar(255) NOT NULL,
  `rule_content` mediumtext NOT NULL,
  `sensor` varchar(255) NOT NULL,
  `sensor_meta` mediumtext,
  `user_id` varchar(255) DEFAULT NULL,
  `reasoning_id` bigint(20) NOT NULL,
  `latitude` float DEFAULT '0',
  `longitude` float DEFAULT '0',
  `quantity_kind` varchar(255) DEFAULT NULL,
  `unit_of_measurement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlgmicuxofgcs1nmr6ni9an45o` (`reasoning_id`),
  CONSTRAINT `FKlgmicuxofgcs1nmr6ni9an45o` FOREIGN KEY (`reasoning_id`) REFERENCES `reasoning` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-03 11:28:12
