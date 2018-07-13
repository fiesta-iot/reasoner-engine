CREATE DATABASE  IF NOT EXISTS `fiesta_reasoner_engine` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fiesta_reasoner_engine`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: fiesta_reasoner_engine
-- ------------------------------------------------------
-- Server version	5.7.15

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
-- Table structure for table `execution`
--

DROP TABLE IF EXISTS `execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `execution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `ended` datetime DEFAULT NULL,
  `execute_type` int(11) DEFAULT NULL,
  `full_data` mediumtext,
  `inffered_data` mediumtext,
  `original_data` mediumtext,
  `rule_content` mediumtext,
  `sensor` varchar(350) DEFAULT NULL,
  `started` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `user_id` varchar(350) DEFAULT NULL,
  `register_rule_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfmif44x8cwqncue8cxclwufs` (`register_rule_id`),
  CONSTRAINT `FKfmif44x8cwqncue8cxclwufs` FOREIGN KEY (`register_rule_id`) REFERENCES `register_rule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reasoning`
--

DROP TABLE IF EXISTS `reasoning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reasoning` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `created` datetime DEFAULT NULL,
  `description` varchar(350) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `name` varchar(350) NOT NULL,
  `quantity_kind` varchar(350) DEFAULT NULL,
  `rule_type` int(11) NOT NULL,
  `sensor` varchar(350) NOT NULL,
  `sensor_endp` varchar(350) DEFAULT NULL,
  `sensor_meta` mediumtext,
  `sensor_sample_data` mediumtext,
  `unit_of_measurement` varchar(350) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `user_id` varchar(350) NOT NULL,
  `display_sensor_id` varchar(350) DEFAULT NULL,
  `hashed_sensor` varchar(350) DEFAULT NULL,
  `non_expert_original_rules` mediumtext,
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
  `description` varchar(350) DEFAULT NULL,
  `full_data` mediumtext,
  `inferred_data` mediumtext,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `name` varchar(350) NOT NULL,
  `quantity_kind` varchar(350) DEFAULT NULL,
  `rule_content` mediumtext NOT NULL,
  `sensor` varchar(350) NOT NULL,
  `sensor_endp` varchar(350) DEFAULT NULL,
  `sensor_meta` mediumtext,
  `unit_of_measurement` varchar(350) DEFAULT NULL,
  `user_id` varchar(350) DEFAULT NULL,
  `reasoning_id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `hashed_sensor` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlgmicuxofgcs1nmr6ni9an45o` (`reasoning_id`),
  CONSTRAINT `FKlgmicuxofgcs1nmr6ni9an45o` FOREIGN KEY (`reasoning_id`) REFERENCES `reasoning` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-27 11:05:00
