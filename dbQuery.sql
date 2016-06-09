-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.22-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for fcs_stage
CREATE DATABASE IF NOT EXISTS `fcs_stage` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fcs_stage`;


-- Dumping structure for table fcs_stage.collection_request
CREATE TABLE IF NOT EXISTS `collection_request` (
  `req_name` varchar(50) DEFAULT NULL,
  `req_location` varchar(50) DEFAULT NULL,
  `req_address` varchar(500) DEFAULT NULL,
  `req_contact` varchar(50) DEFAULT NULL,
  `req_quantity` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `req_number` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fcs_stage.collection_request: ~1 rows (approximately)
DELETE FROM `collection_request`;
/*!40000 ALTER TABLE `collection_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `collection_request` ENABLE KEYS */;


-- Dumping structure for table fcs_stage.collector_availability
CREATE TABLE IF NOT EXISTS `collector_availability` (
  `user_email` varchar(50),
  `status` varchar(50) DEFAULT NULL,
  `qty` varchar(50) DEFAULT NULL,
  `maxDeleveryTime` varchar(50) DEFAULT NULL,
  `currentLocation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table fcs_stage.collector_availability: ~9 rows (approximately)
DELETE FROM `collector_availability`;
/*!40000 ALTER TABLE `collector_availability` DISABLE KEYS */;
INSERT INTO `collector_availability` (`user_email`, `status`, `qty`, `maxDeleveryTime`, `currentLocation`) VALUES
	('me.prashantghuge@gmail.com', 'Idle', '25', NULL, '18.5635511,73.9325552'),
	('jsr@gmail.com', 'Idle', '250', NULL, '18.5073985,73.8076504'),
	('ghuge@gmail.com', 'Idle', '300', NULL, '18.4666576,73.8258668'),
	('test1@gmail.com', 'Idle', '200', NULL, '18.5308225,73.8474647'),
	('132@.sd', 'Idle', '110', NULL, '18.5934685,73.79291119999999'),
	('shri@gmail.com', 'Idle', '86', NULL, '18.5514501,73.9347856'),
	('test2@gmail.com', 'Idle', '100', NULL, '18.6297811,73.7997094'),
	('t3@gmail.com', 'Idle', '50', NULL, '18.6297811,73.7997094'),
	('t4@gmail.com', 'Idle', '12', NULL, '18.6297811,73.7997094');
/*!40000 ALTER TABLE `collector_availability` ENABLE KEYS */;


-- Dumping structure for table fcs_stage.request_mapping
CREATE TABLE IF NOT EXISTS `request_mapping` (
  `RequestNo` varchar(50) DEFAULT NULL,
  `collectorIds` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fcs_stage.request_mapping: ~1 rows (approximately)
DELETE FROM `request_mapping`;
/*!40000 ALTER TABLE `request_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_mapping` ENABLE KEYS */;


-- Dumping structure for table fcs_stage.user
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table fcs_stage.user: ~10 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`username`, `password`, `token`, `role`, `email`, `mobile`) VALUES
	('test2', 'qwe', NULL, NULL, '132@.sd', '132'),
	('admin', 'shri', '1070265207', 'admin', '2', ''),
	('shrikant', 'shri', NULL, NULL, 'ghuge@gmail.com', '7987987984'),
	('Jai Shri', 'shrikant', NULL, NULL, 'jsr@gmail.com', '7897987987'),
	('Prashant', 'prashant', NULL, NULL, 'me.prashantghuge@gmail.com', '7709582219'),
	('shrikant', '798', NULL, NULL, 'shri@gmail.com', '798'),
	('test3up', '789', NULL, NULL, 't3@gmail.com', '798'),
	('test4', '132', NULL, NULL, 't4@gmail.com', '7987987987'),
	('Test1', '123', NULL, NULL, 'test1@gmail.com', '132'),
	('test2', '132', NULL, NULL, 'test2@gmail.com', '132');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
