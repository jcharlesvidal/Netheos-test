DROP DATABASE IF EXISTS `faq`;
CREATE DATABASE IF NOT EXISTS `faq` DEFAULT CHARSET=latin1;

USE `faq`;

DROP TABLE IF EXISTS `faq`;

CREATE TABLE `faq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `reponse` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

LOCK TABLES `faq` WRITE;
INSERT INTO `faq` VALUES (1,'question1','reponse1'),(2,'question2','reponse2'),(3,'question3','reponse3'),(4,'question4','reponse4');
UNLOCK TABLES;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `value_UNIQUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

LOCK TABLES `tag` WRITE;
INSERT INTO `tag` VALUES (1,'tag1'),(2,'tag2'),(3,'tag3'),(4,'tag4');
UNLOCK TABLES;

DROP TABLE IF EXISTS `faq_tag`;
CREATE TABLE `faq_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `faq_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_faq_tag_1_idx` (`faq_id`),
  KEY `fk_faq_tag_2_idx` (`tag_id`),
  CONSTRAINT `fk_faq_tag_1` FOREIGN KEY (`faq_id`) REFERENCES `faq` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_faq_tag_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

LOCK TABLES `faq_tag` WRITE;
INSERT INTO `faq_tag` VALUES (1,1,1),(2,2,1),(3,2,2),(4,3,2),(5,3,1),(6,3,3),(7,4,1),(8,4,2),(9,4,3);
UNLOCK TABLES;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
UNLOCK TABLES;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_1_idx` (`role_id`),
  CONSTRAINT `fk_user_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'admin','admin',1),(2,'user','user',2);
UNLOCK TABLES;

