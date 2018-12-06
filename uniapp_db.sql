-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2018 at 06:01 PM
-- Server version: 5.6.15-log
-- PHP Version: 5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `uni`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `code` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `credits` int(11) NOT NULL,
  `teacher` varchar(50) DEFAULT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`code`, `name`, `credits`, `teacher`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`) VALUES
('GLG204', 'Architectures Logicielles Java(2)', 6, 'USER001', '2018-09-14', 'USER001', NULL, NULL, 1),
('MVA004', 'Automates, codes, graphes et matrices', 6, 'TEA001', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1),
('NFA007', 'Méthodes pour l''Informatisation', 4, 'USER001', '2018-09-15', 'USER001', NULL, NULL, 1),
('NFA008', 'Bases de données', 6, 'USER001', '2018-09-15', 'USER001', NULL, NULL, 1),
('NFA017', 'Développement Web (2) :sites dynamiques et serveur', 6, 'USER001', '2018-09-15', 'USER001', NULL, NULL, 1),
('NFA021', 'Développement Web (3): Mise en pratique', 6, 'USER001', '2018-09-14', 'USER001', NULL, NULL, 1);

--
-- Triggers `course`
--
DROP TRIGGER IF EXISTS `course_delete`;
DELIMITER //
CREATE TRIGGER `course_delete` AFTER DELETE ON `course`
 FOR EACH ROW INSERT INTO course_hist VALUES (
 OLD.code, OLD.name, OLD.credits, OLD.created_date, OLD.created_by, OLD.updated_date, OLD.updated_by, OLD.activated, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `course_insert`;
DELIMITER //
CREATE TRIGGER `course_insert` AFTER INSERT ON `course`
 FOR EACH ROW INSERT INTO course_hist VALUES(
 NEW.code, NEW.name, NEW.credits, NEW.created_date, NEW.created_by, NEW.updated_date, NEW.updated_by, NEW.activated,  'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `course_update`;
DELIMITER //
CREATE TRIGGER `course_update` AFTER UPDATE ON `course`
 FOR EACH ROW INSERT INTO course_hist VALUES (
 NEW.code, NEW.name, NEW.credits, NEW.created_date, NEW.created_by, NEW.updated_date, NEW.updated_by, NEW.activated, 'record updated', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `coursestudent`
--

CREATE TABLE IF NOT EXISTS `coursestudent` (
  `course` varchar(50) NOT NULL,
  `student` varchar(50) NOT NULL,
  `degree` varchar(20) NOT NULL,
  `semester` int(11) NOT NULL,
  `cur_year` int(11) DEFAULT NULL,
  `cur_semester` int(11) DEFAULT NULL,
  `grade1` double DEFAULT NULL,
  `grade2` double DEFAULT NULL,
  `finalgrade` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `coursestudent`
--

INSERT INTO `coursestudent` (`course`, `student`, `degree`, `semester`, `cur_year`, `cur_semester`, `grade1`, `grade2`, `finalgrade`) VALUES
('GLG204', 'USER001', 'LG025', 1, 2018, 1, NULL, NULL, NULL),
('MVA004', 'USER001', 'LG025', 1, 2018, 1, 10, 0, 0),
('MVA004', 'XNGGWW', 'LG025', 1, 2018, 1, 15, 0, 0),
('NFA007', 'XNGGWW', 'LG025', 1, 2018, 1, NULL, NULL, NULL),
('MVA004', 'Y5PFZR', 'LG025', 1, 2018, 1, 13, 0, 0),
('NFA007', 'Y5PFZR', 'LG025', 1, 2018, 1, NULL, NULL, NULL);

--
-- Triggers `coursestudent`
--
DROP TRIGGER IF EXISTS `coursestudent_delete`;
DELIMITER //
CREATE TRIGGER `coursestudent_delete` AFTER DELETE ON `coursestudent`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
old.course, old.student, old.degree, old.semester, old.grade1, old.grade2, old.finalgrade, 'record deleted', now() )
//
DELIMITER ;
DROP TRIGGER IF EXISTS `coursestudent_insert`;
DELIMITER //
CREATE TRIGGER `coursestudent_insert` AFTER INSERT ON `coursestudent`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
 NEW.course, NEW.student, NEW.degree, NEW.semester, NEW.grade1, NEW.grade2, NEW.finalgrade,  'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `coursestudent_update`;
DELIMITER //
CREATE TRIGGER `coursestudent_update` AFTER UPDATE ON `coursestudent`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
 NEW.course, NEW.student, NEW.degree, NEW.semester, NEW.grade1, NEW.grade2, NEW.finalgrade,  'record updated', now() )
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `coursestudent_hist`
--

CREATE TABLE IF NOT EXISTS `coursestudent_hist` (
  `course` varchar(50) NOT NULL,
  `student` varchar(50) NOT NULL,
  `degree` varchar(100) NOT NULL,
  `semester` int(11) NOT NULL,
  `grade1` double DEFAULT NULL,
  `grade2` double DEFAULT NULL,
  `finalgrade` double DEFAULT NULL,
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `coursestudent_hist`
--

INSERT INTO `coursestudent_hist` (`course`, `student`, `degree`, `semester`, `grade1`, `grade2`, `finalgrade`, `action`, `action_date`) VALUES
('GLG204', 'USER001', '', 2, 12.05, 14.2, 13.34, 'record inserted', '2018-09-15'),
('GLG204', 'USER001', '', 2, 12.05, 14.2, 13.34, 'record deleted', '2018-09-29');

-- --------------------------------------------------------

--
-- Table structure for table `course_hist`
--

CREATE TABLE IF NOT EXISTS `course_hist` (
  `code` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `credits` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course_hist`
--

INSERT INTO `course_hist` (`code`, `name`, `credits`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`, `action`, `action_date`) VALUES
('NFA021', 'Développement Web (3): Mise en pratique', 6, '2018-09-14', 'joelle tannous', NULL, NULL, 1, 'record inserted', '2018-09-14'),
('NFA021', 'Développement Web (3): Mise en pratique', 6, '2018-09-14', 'Joelle Tannous', NULL, NULL, 1, 'record updated', '2018-09-15'),
('GLG204', 'Architectures Logicielles Java(2)', 6, '2018-09-14', 'Joelle Tannous', NULL, NULL, 1, 'record updated', '2018-09-15'),
('NFA017', 'Développement Web (2) :sites dynamiques et serveur', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('MVA004', 'Automates, codes, graphes et matrices', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('GLG204', 'Architectures Logicielles Java(2)', 6, '2018-09-14', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-15'),
('NFA021', 'Développement Web (3): Mise en pratique', 6, '2018-09-14', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-15'),
('NFA008', 'Bases de données', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('NFA007', 'Méthodes pour l''Informatisation', 4, '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('MVA004', 'Automates, codes, graphes et matrices', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-10-01'),
('GLG204', 'Architectures Logicielles Java(2)', 6, '2018-09-14', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('MVA004', 'Automates, codes, graphes et matrices', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('NFA007', 'Méthodes pour l''Informatisation', 4, '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('NFA008', 'Bases de données', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('NFA017', 'Développement Web (2) :sites dynamiques et serveur', 6, '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('NFA021', 'Développement Web (3): Mise en pratique', 6, '2018-09-14', 'USER001', NULL, NULL, 1, 'record updated', '2018-12-06'),
('MVA004', 'Automates, codes, graphes et matrices', 6, '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `degree`
--

CREATE TABLE IF NOT EXISTS `degree` (
  `code` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `department` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degree`
--

INSERT INTO `degree` (`code`, `name`, `price`, `department`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`) VALUES
('CYC9101', 'Architecture et ingénierie systèmes logiciels AISL', 70003, 'INF001', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1),
('LG025', 'Liscence STIC', 50000, 'INF001', '2018-09-15', 'USER001', NULL, NULL, 1);

--
-- Triggers `degree`
--
DROP TRIGGER IF EXISTS `degree_delete`;
DELIMITER //
CREATE TRIGGER `degree_delete` AFTER DELETE ON `degree`
 FOR EACH ROW INSERT INTO degree_hist VALUES (
OLD.code, OLD.name, OLD.price, OLD.department, OLD.created_date, OLD.created_by, OLD.updated_date, OLD.updated_by, OLD.activated, 'record deleted', now() )
//
DELIMITER ;
DROP TRIGGER IF EXISTS `degree_insert`;
DELIMITER //
CREATE TRIGGER `degree_insert` AFTER INSERT ON `degree`
 FOR EACH ROW INSERT INTO degree_hist VALUES (
 new.code, new.name, new.price, new.department, new.created_date, new.created_by, new.updated_date, new.updated_by, new.activated,  'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `degree_update`;
DELIMITER //
CREATE TRIGGER `degree_update` AFTER UPDATE ON `degree`
 FOR EACH ROW INSERT INTO degree_hist VALUES (
 new.code, new.name, new.price, new.department, new.created_date, new.created_by, new.updated_date, new.updated_by, new.activated,  'record updated', now() )
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `degreecourse`
--

CREATE TABLE IF NOT EXISTS `degreecourse` (
  `degree` varchar(50) NOT NULL,
  `course` varchar(50) NOT NULL,
  `semester` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(8) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degreecourse`
--

INSERT INTO `degreecourse` (`degree`, `course`, `semester`, `created_date`, `created_by`, `updated_date`, `updated_by`) VALUES
('CYC9101', 'GLG204', 2, '0000-00-00', '', '0000-00-00', ''),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', ''),
('LG025', 'NFA008', 1, '0000-00-00', '', '0000-00-00', ''),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', ''),
('LG025', 'NFA007', 1, '0000-00-00', '', '0000-00-00', ''),
('CYC9101', 'MVA004', 1, '2018-12-06', 'USER001', NULL, NULL),
('CYC9101', 'NFA008', 2, '2018-12-06', 'USER001', NULL, NULL);

--
-- Triggers `degreecourse`
--
DROP TRIGGER IF EXISTS `degreecourse_delete`;
DELIMITER //
CREATE TRIGGER `degreecourse_delete` AFTER DELETE ON `degreecourse`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
 old.degree, old.course, old.semester, old.created_date, old.created_by,old.updated_date,old.updated_by, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `degreecourse_insert`;
DELIMITER //
CREATE TRIGGER `degreecourse_insert` AFTER INSERT ON `degreecourse`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
 NEW.degree, NEW.course, NEW.semester, new.created_date, new.created_by,null,null, 'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `degreecourse_update`;
DELIMITER //
CREATE TRIGGER `degreecourse_update` AFTER UPDATE ON `degreecourse`
 FOR EACH ROW INSERT INTO degreecourse_hist VALUES (
 NEW.degree, NEW.course, NEW.semester,  'record updated', now() )
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `degreecourse_hist`
--

CREATE TABLE IF NOT EXISTS `degreecourse_hist` (
  `degree` varchar(50) NOT NULL,
  `course` varchar(50) NOT NULL,
  `semester` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(8) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(8) DEFAULT NULL,
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degreecourse_hist`
--

INSERT INTO `degreecourse_hist` (`degree`, `course`, `semester`, `created_date`, `created_by`, `updated_date`, `updated_by`, `action`, `action_date`) VALUES
('LG025', 'NFA021', 2, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-15'),
('CYC9101', 'GLG204', 2, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-15'),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'GLG204', 6, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'GLG204', 6, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'GLG204', 6, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'GLG204', 6, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'NFA021', 2, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'NFA021', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'NFA021', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'NFA021', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'NFA021', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-09-28'),
('LG025', 'MVA004', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-28'),
('LG025', 'NFA008', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-29'),
('LG025', 'GLG204', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-29'),
('LG025', 'NFA007', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-09-29'),
('CYC9101', 'MVA004', 3, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-12-02'),
('CYC9101', 'MVA004', 3, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-12-02'),
('CYC9101', 'NFA007', 0, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-12-02'),
('CYC9101', 'NFA007', 0, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-12-02'),
('CYC9101', 'NFA007', 1, '0000-00-00', '', '0000-00-00', '', 'record inserted', '2018-12-02'),
('CYC9101', 'MVA004', 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('CYC9101', 'NFA008', 2, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('CYC9101', 'NFA007', 1, '0000-00-00', '', '0000-00-00', '', 'record deleted', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `degree_hist`
--

CREATE TABLE IF NOT EXISTS `degree_hist` (
  `code` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `department` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `degree_hist`
--

INSERT INTO `degree_hist` (`code`, `name`, `price`, `department`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`, `action`, `action_date`) VALUES
('LG025', 'Liscence Informatique', 50000, 'INF001', '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('LG025', 'Liscence STIC', 50000, 'INF001', '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-15'),
('CYC9101', 'Architecture et ingénierie systèmes logiciels AISL', 70000, 'INF001', '2018-09-15', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-15'),
('CYC9101', 'Architecture et ingénierie systèmes logiciels AISL', 70001, 'INF001', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06'),
('CYC9101', 'Architecture et ingénierie systèmes logiciels AISL', 70002, 'INF001', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06'),
('CYC9101', 'Architecture et ingénierie systèmes logiciels AISL', 70003, 'INF001', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE IF NOT EXISTS `department` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `head` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`code`, `name`, `head`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`) VALUES
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', '2018-12-02', 'USER001', 1),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', '2018-12-06', 'USER001', 1),
('ppo', 'abc', 'C002', '2018-09-27', 'USER001', '2018-12-01', 'USER001', 1),
('RPR', 'Architectureee', 'C002', '2018-09-26', 'USER001', '2018-12-01', 'USER001', 1),
('TEST', 'TEST DEP', 'C001', '2018-09-27', 'USER001', '2018-12-02', 'USER001', 1);

--
-- Triggers `department`
--
DROP TRIGGER IF EXISTS `department_delete`;
DELIMITER //
CREATE TRIGGER `department_delete` AFTER DELETE ON `department`
 FOR EACH ROW INSERT INTO department_hist VALUES (
 OLD.code, OLD.name, OLD.head, OLD.created_date, OLD.created_by, OLD.updated_date, OLD.updated_by, OLD.activated, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `department_insert`;
DELIMITER //
CREATE TRIGGER `department_insert` AFTER INSERT ON `department`
 FOR EACH ROW INSERT INTO department_hist VALUES(
 NEW.code, NEW.name, NEW.head, NEW.created_date, NEW.created_by, New.updated_date, NEW.updated_by, NEW.activated, 'record inserted', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `department_hist`
--

CREATE TABLE IF NOT EXISTS `department_hist` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `head` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department_hist`
--

INSERT INTO `department_hist` (`code`, `name`, `head`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`, `action`, `action_date`) VALUES
('INF001', 'Informatique', 'Pascal Fares', '2018-09-14', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-14'),
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', NULL, NULL, 0, 'record inserted', '2018-09-15'),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-15'),
('xx', 'test', 'u1', '2018-09-26', 'u1', NULL, NULL, 1, 'record inserted', '2018-09-26'),
('xx', 'test', 'u1', '2018-09-26', 'u1', NULL, NULL, 1, 'record deleted', '2018-09-26'),
('RPR', 'Architecture', 'C001', '2018-09-26', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-26'),
('RPR', 'Architectureee', 'C001', '2018-09-26', 'USER001', '2018-09-26', 'USER001', 1, 'record updated', '2018-09-26'),
('TEST', 'TEST DEP', 'C001', '2018-09-27', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-27'),
('ppo', 'abc', 'C001', '2018-09-27', 'USER001', NULL, NULL, 1, 'record inserted', '2018-09-27'),
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', NULL, NULL, 0, 'record updated', '2018-09-28'),
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-28'),
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', NULL, NULL, 1, 'record updated', '2018-09-28'),
('INF001', 'Informatique', 'C002', '2018-09-14', 'USER001', '2018-12-01', 'USER001', 1, 'record updated', '2018-12-01'),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', '2018-12-01', 'USER001', 1, 'record updated', '2018-12-01'),
('ppo', 'abc', 'C002', '2018-09-27', 'USER001', '2018-12-01', 'USER001', 1, 'record updated', '2018-12-01'),
('TEST', 'TEST DEP', 'C002', '2018-09-27', 'USER001', '2018-12-01', 'USER001', 1, 'record updated', '2018-12-01'),
('RPR', 'Architectureee', 'C002', '2018-09-26', 'USER001', '2018-12-01', 'USER001', 1, 'record updated', '2018-12-01'),
('INF001', 'Informatique', 'C002', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('CIV002', 'Civil', 'C001', '2018-09-15', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('CIV002', 'Civil', 'C002', '2018-09-15', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('INF001', 'Informatique', 'C002', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('INF001', 'Informatique', 'C002', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('INF001', 'Informatique', 'C001', '2018-09-14', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02'),
('TEST', 'TEST DEP', 'C001', '2018-09-27', 'USER001', '2018-12-02', 'USER001', 1, 'record updated', '2018-12-02');

-- --------------------------------------------------------

--
-- Table structure for table `global`
--

CREATE TABLE IF NOT EXISTS `global` (
  `curr_semester` varchar(50) NOT NULL,
  `curr_year` varchar(50) NOT NULL,
  `period` varchar(50) NOT NULL,
  `currency` varchar(50) NOT NULL,
  `opening_days` varchar(50) NOT NULL,
  `updated_by` varchar(50) NOT NULL,
  `updated_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `global`
--

INSERT INTO `global` (`curr_semester`, `curr_year`, `period`, `currency`, `opening_days`, `updated_by`, `updated_date`) VALUES
('1', '2018', '3', 'LBP', '5', 'USER001', '2018-12-06');

--
-- Triggers `global`
--
DROP TRIGGER IF EXISTS `global_update`;
DELIMITER //
CREATE TRIGGER `global_update` AFTER UPDATE ON `global`
 FOR EACH ROW INSERT INTO global_hist VALUES (
new.curr_semester, new.curr_year, new.period, new.currency, new.opening_days, new.updated_by, new.updated_date, 'record updated', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `global_hist`
--

CREATE TABLE IF NOT EXISTS `global_hist` (
  `curr_semester` varchar(50) NOT NULL,
  `curr_year` varchar(50) NOT NULL,
  `period` varchar(50) NOT NULL,
  `currency` varchar(50) NOT NULL,
  `opening_days` varchar(50) NOT NULL,
  `updated_by` varchar(50) NOT NULL,
  `updated_date` date NOT NULL,
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `global_hist`
--

INSERT INTO `global_hist` (`curr_semester`, `curr_year`, `period`, `currency`, `opening_days`, `updated_by`, `updated_date`, `action`, `action_date`) VALUES
('1', '2018', '3', 'USD', '5', 'USER001', '2018-12-06', 'record updated', '2018-12-06'),
('1', '2018', '3', 'LBP', '5', 'USER001', '2018-12-06', 'record updated', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `code` varchar(1) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`code`, `name`) VALUES
('A', 'Admin'),
('H', 'Head Of Department'),
('S', 'Student'),
('T', 'Teacher');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE IF NOT EXISTS `schedule` (
  `course` varchar(50) NOT NULL,
  `day` varchar(20) NOT NULL,
  `period` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `semester` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`course`, `day`, `period`, `year`, `semester`, `created_date`, `created_by`, `updated_date`, `updated_by`) VALUES
('GLG204', '0', 1, 2018, 1, '2018-11-28', 'USER001', NULL, NULL),
('MVA004', '1', 2, 2018, 1, '2018-11-28', 'USER001', NULL, NULL),
('NFA008', '3', 2, 2018, 1, '2018-12-02', 'USER001', NULL, NULL),
('GLG204', '3', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL),
('GLG204', '1', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL),
('GLG204', '4', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL),
('NFA008', '4', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL),
('NFA017', '2', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL);

--
-- Triggers `schedule`
--
DROP TRIGGER IF EXISTS `department_update`;
DELIMITER //
CREATE TRIGGER `department_update` AFTER UPDATE ON `schedule`
 FOR EACH ROW INSERT INTO schedule_hist VALUES (
 new.course, new.day, new.period, new.year, new.semester, new.created_date, new.created_by, new.updated_date, new.updated_by, 'record updated', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `schedule_delete`;
DELIMITER //
CREATE TRIGGER `schedule_delete` AFTER DELETE ON `schedule`
 FOR EACH ROW INSERT INTO schedule_hist VALUES (
 old.course, old.day, old.period, old.year, old.semester, old.created_date, old.created_by, old.updated_date, old.updated_by, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `schedule_insert`;
DELIMITER //
CREATE TRIGGER `schedule_insert` AFTER INSERT ON `schedule`
 FOR EACH ROW INSERT INTO schedule_hist VALUES(
 new.course, new.day, new.period, new.year, new.semester, new.created_date, new.created_by, new.updated_date, new.updated_by, 'record inserted', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `schedule_hist`
--

CREATE TABLE IF NOT EXISTS `schedule_hist` (
  `course` varchar(50) NOT NULL,
  `day` varchar(20) NOT NULL,
  `period` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `semester` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `schedule_hist`
--

INSERT INTO `schedule_hist` (`course`, `day`, `period`, `year`, `semester`, `created_date`, `created_by`, `updated_date`, `updated_by`, `action`, `action_date`) VALUES
('GLG204', '2', 2, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('GLG204', '3', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('GLG204', '2', 2, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record deleted', '2018-12-06'),
('GLG204', '2', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('GLG204', '1', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('GLG204', '4', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('NFA008', '4', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('NFA017', '2', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record inserted', '2018-12-06'),
('GLG204', '2', 1, 2018, 1, '2018-12-06', 'USER001', NULL, NULL, 'record deleted', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `semester`
--

CREATE TABLE IF NOT EXISTS `semester` (
  `number` int(11) NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `year` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `semester`
--

INSERT INTO `semester` (`number`, `start`, `end`, `year`, `created_date`, `created_by`, `updated_date`, `updated_by`) VALUES
(1, '2018-09-01', '2019-02-28', 2018, '2018-09-14', 'USER001', NULL, NULL);

--
-- Triggers `semester`
--
DROP TRIGGER IF EXISTS `semester_delete`;
DELIMITER //
CREATE TRIGGER `semester_delete` AFTER DELETE ON `semester`
 FOR EACH ROW INSERT INTO semester_hist VALUES (
NULL, OLD.number, OLD.start, OLD.end, OLD.year, OLD.created_date, OLD.created_by, OLD.updated_date, OLD.updated_by, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `semester_insert`;
DELIMITER //
CREATE TRIGGER `semester_insert` AFTER INSERT ON `semester`
 FOR EACH ROW INSERT INTO semester_hist VALUES (
NULL, NEW.number, NEW.start, New.end, NEW.year, NEW.created_date, NEW.created_by, NEW.updated_date, NEW.updated_by, 'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `semester_update`;
DELIMITER //
CREATE TRIGGER `semester_update` AFTER UPDATE ON `semester`
 FOR EACH ROW INSERT INTO semester_hist VALUES (
NULL, NEW.number, NEW.start, NEW.end, NEW.year, NEW.created_date, New.created_by, NEW.updated_date, NEW.updated_by, 'record updated', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `family` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `adr` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`code`, `name`, `family`, `username`, `phone`, `email`, `adr`, `role`, `pass`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`) VALUES
('C001', 'Pascal', 'FARES', '856c', 3122, 'pascal.fares@isae.edu.lb', 'beirout', 'H', '852456', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1),
('C002', 'Chef', 'CIVIL', '963c', NULL, 'chef.civil@isae.edu.lb', 'nahr brahim', 'H', 'pa55word', '2018-09-15', 'USER001', NULL, NULL, 1),
('TEA001', 'Georges', 'AL HAJAL', '695tea', NULL, 'georges.alhajal@isae.edu.lb', 'jbeil', 'T', '123456', '2018-09-15', 'USER001', NULL, NULL, 1),
('USER001', 'Joelle', 'Tannous', 'hello', NULL, 'joelle.tannous@isae.edu.lb', 'okaibe', 'A', 'admin1', '2018-09-15', 'USER001', NULL, NULL, 1),
('XNGGWW', 'Ralph', 'Saade', 'ralph', 70120805, 'ralph_saade@hotmail.com', 'jounieh', 'S', 'ralph', '2018-09-28', 'USER001', '2018-12-06', 'USER001', 1),
('Y5PFZR', 'Marc ', 'Saade', 'marc', 70120, 'mdsd', 'sdd', 'S', 'marc', '2018-10-01', 'USER001', NULL, NULL, 1);

--
-- Triggers `user`
--
DROP TRIGGER IF EXISTS `user_delete`;
DELIMITER //
CREATE TRIGGER `user_delete` AFTER DELETE ON `user`
 FOR EACH ROW INSERT INTO user_hist VALUES (
 old.code, old.name, old.family, old.username, old.phone, old.email, old.adr, old.role, old.pass, old.created_date, old.created_by, old.updated_date, old.updated_by, old.activated, 'record deleted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `user_insert`;
DELIMITER //
CREATE TRIGGER `user_insert` AFTER INSERT ON `user`
 FOR EACH ROW INSERT INTO user_hist VALUES (
 new.code, new.name, new.family, new.username, new.phone, new.email, new.adr, new.role, new.pass, new.created_date, new.created_by, new.updated_date, new.updated_by, new.activated, 'record inserted', now()
)
//
DELIMITER ;
DROP TRIGGER IF EXISTS `user_update`;
DELIMITER //
CREATE TRIGGER `user_update` AFTER UPDATE ON `user`
 FOR EACH ROW INSERT INTO user_hist VALUES (
 old.code, old.name, old.family, old.username, old.phone, old.email, old.adr, old.role, old.pass, old.created_date, old.created_by, old.updated_date, old.updated_by, old.activated, 'record updated', now()
)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `user_hist`
--

CREATE TABLE IF NOT EXISTS `user_hist` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `family` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `adr` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  `action` varchar(50) NOT NULL,
  `action_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_hist`
--

INSERT INTO `user_hist` (`code`, `name`, `family`, `username`, `phone`, `email`, `adr`, `role`, `pass`, `created_date`, `created_by`, `updated_date`, `updated_by`, `activated`, `action`, `action_date`) VALUES
('C001', 'Pascal', 'FARES', '856c', 31, 'pascal.fares@isae.edu.lb', 'beirout', 'H', '852456', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06'),
('C001', 'Pascal', 'FARES', '856c', 3122, 'pascal.fares@isae.edu.lb', 'beirout', 'H', '852456', '2018-09-15', 'USER001', '2018-12-06', 'USER001', 1, 'record updated', '2018-12-06'),
('XNGGWW', 'Ralph', 'Saade', 'ralph', 70120802, 'ralph_saade@hotmail.com', 'jounieh', 'S', 'ralph', '2018-09-28', 'USER001', '2018-09-28', 'USER001', 1, 'record updated', '2018-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `user_logs`
--

CREATE TABLE IF NOT EXISTS `user_logs` (
  `user` varchar(50) NOT NULL,
  `login_date` date NOT NULL,
  `logout_date` date DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_logs`
--

INSERT INTO `user_logs` (`user`, `login_date`, `logout_date`, `id`) VALUES
('hello', '2018-12-06', NULL, 1),
('hello', '2018-12-06', NULL, 2),
('hello', '2018-12-06', NULL, 3),
('hello', '2018-12-06', NULL, 4),
('hello', '2018-12-06', NULL, 5),
('hello', '2018-12-06', NULL, 6),
('hello', '2018-12-06', NULL, 7),
('hello', '2018-12-06', NULL, 8),
('hello', '2018-12-06', NULL, 9),
('hello', '2018-12-06', NULL, 10),
('hello', '2018-12-06', NULL, 11),
('hello', '2018-12-06', NULL, 12),
('hello', '2018-12-06', NULL, 13),
('hello', '2018-12-06', NULL, 14),
('hello', '2018-12-06', NULL, 15),
('hello', '2018-12-06', NULL, 16),
('hello', '2018-12-06', '2018-12-06', 17),
('hello', '2018-12-06', NULL, 18),
('hello', '2018-12-06', NULL, 19),
('hello', '2018-12-06', '2018-12-06', 20),
('hello', '2018-12-06', '2018-12-06', 21),
('hello', '2018-12-06', '2018-12-06', 22),
('hello', '2018-12-06', NULL, 23),
('hello', '2018-12-06', NULL, 24),
('hello', '2018-12-06', '2018-12-06', 25),
('hello', '2018-12-06', NULL, 26),
('hello', '2018-12-06', NULL, 27),
('hello', '2018-12-06', NULL, 28),
('hello', '2018-12-06', NULL, 29);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
