-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: gestion_domination
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `id_administrador` int NOT NULL,
  `nombre_usuario` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `rol` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id_administrador`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'admin','$2a$10$g6YJKPmv0BZ5nL0HWCtbyevQQir4KIU7HItkLuGnXFFn08tUpr48.','administrador');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `usuario_idusuario` int NOT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_cliente_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (2,5),(29,46),(30,47),(31,48);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domicilio`
--

DROP TABLE IF EXISTS `domicilio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domicilio` (
  `id_domicilio` int NOT NULL AUTO_INCREMENT,
  `calle` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `altura` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `localidad` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `partido` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `provincia` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `sucursal_idsucursal` int NOT NULL,
  PRIMARY KEY (`id_domicilio`),
  KEY `fk_domicilio_sucursal1_idx` (`sucursal_idsucursal`),
  CONSTRAINT `fk_domicilio_sucursal1` FOREIGN KEY (`sucursal_idsucursal`) REFERENCES `sucursal` (`id_sucursal`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domicilio`
--

LOCK TABLES `domicilio` WRITE;
/*!40000 ALTER TABLE `domicilio` DISABLE KEYS */;
INSERT INTO `domicilio` VALUES (12,'Rincón ','564','San Fernando','San Fernando','Buenos Aires',14),(23,'25 de mayo','400','San Isidro','San Isidro','Buenos Aires',26),(24,'av. San Martin','123','Caseros','Tres de Febrero','Buenos Aires',31),(27,'Dr. Rebizzo','1515','Caseros','Tres de Febrero','Buenos Aires',34),(28,'el Kairser','69','Garin','Escobar','Catamarca',35);
/*!40000 ALTER TABLE `domicilio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestador`
--

DROP TABLE IF EXISTS `prestador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestador` (
  `id_prestador` int NOT NULL AUTO_INCREMENT,
  `usuario_idusuario` int NOT NULL,
  PRIMARY KEY (`id_prestador`),
  KEY `fk_prestador_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_prestador_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestador`
--

LOCK TABLES `prestador` WRITE;
/*!40000 ALTER TABLE `prestador` DISABLE KEYS */;
INSERT INTO `prestador` VALUES (3,10),(4,18),(5,49);
/*!40000 ALTER TABLE `prestador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `duracion` double NOT NULL,
  `hora_inicio` datetime NOT NULL,
  `hora_fin` datetime NOT NULL,
  `monto` double NOT NULL,
  `sala_idsala` int NOT NULL,
  `cliente_idcliente` int NOT NULL,
  `fecha_reserva` datetime NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_sala_has_cliente_cliente1_idx` (`cliente_idcliente`),
  KEY `fk_sala_has_cliente_sala1_idx` (`sala_idsala`),
  CONSTRAINT `fk_sala_has_cliente_cliente1` FOREIGN KEY (`cliente_idcliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sala_has_cliente_sala1` FOREIGN KEY (`sala_idsala`) REFERENCES `sala` (`id_sala`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` VALUES (30,1,'2024-10-04 16:00:00','2024-10-04 17:00:00',250000,49,2,'2024-10-03 16:05:03');
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sala` (
  `id_sala` int NOT NULL AUTO_INCREMENT,
  `num_sala` int NOT NULL,
  `valor_hora` float NOT NULL,
  `sucursal_idsucursal` int NOT NULL,
  PRIMARY KEY (`id_sala`),
  KEY `fk_sala_sucursal1_idx` (`sucursal_idsucursal`),
  CONSTRAINT `fk_sala_sucursal1` FOREIGN KEY (`sucursal_idsucursal`) REFERENCES `sucursal` (`id_sucursal`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES (9,1,40000,14),(10,2,35000,14),(25,3,250000,14),(26,4,50000,14),(36,1,25000,31),(37,2,25000,31),(38,3,35000,31),(39,4,25000,31),(40,1,251000,26),(49,1,250000,34),(51,2,150000,34),(52,1,2000,35);
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursal`
--

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sucursal` (
  `id_sucursal` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `cant_salas` int NOT NULL,
  `prestador_idprestador` int NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id_sucursal`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_sucursal_prestador_idx` (`prestador_idprestador`),
  CONSTRAINT `fk_sucursal_prestador` FOREIGN KEY (`prestador_idprestador`) REFERENCES `prestador` (`id_prestador`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursal`
--

LOCK TABLES `sucursal` WRITE;
/*!40000 ALTER TABLE `sucursal` DISABLE KEYS */;
INSERT INTO `sucursal` VALUES (14,'\"Lo de Sergio\" Studio',4,3,'12:00:00','23:00:00','02222222222'),(26,'\"Lo de Sergio\" Studio 2 - San Isidro',2,3,'10:00:00','22:00:00','02222222222'),(31,'\"Lo de Sergio\" Studio 3',4,3,'09:00:00','23:00:00','02222222222'),(34,'\"Lo de Sergio\" FAKE',3,4,'10:00:00','23:00:00','12121212'),(35,'JUANPE Studio ',3,5,'01:00:00','22:00:00','696969696969');
/*!40000 ALTER TABLE `sucursal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `apellido` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `celular` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `rol` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `administrador_idadministrador` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`),
  KEY `fk_usuario_administrador1_idx` (`administrador_idadministrador`),
  CONSTRAINT `fk_usuario_administrador1` FOREIGN KEY (`administrador_idadministrador`) REFERENCES `administrador` (`id_administrador`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (5,'Juanpe','Juann','Pelotas','juanpe@asd.com','$2a$10$HnNX1nVAFVEMbjV/FljuIO7Kp0I2ZpWQvPQsw9BFMn2Htnawt/t5i','0800Juanpe','cliente',1),(10,'hola_sergio','Sergio','Vergara','sergio@fakemail.com','$2a$10$Q1qXCWNajYzHyXUpsy64/ud82FMV0qovfd155Lfde98t6bxwxG6fi','15 9999 6666','prestador',1),(18,'Solo_sergio','fakeSergio','fakeVergara','fakefake@fake.com','$2a$10$Rpik8efgLZPUrDCxPPHULeav82.LZNgRq24.d1it622/ombIoDjDq','4545','prestador',1),(46,'usuarioDescarte','usuario','DelAdmin','usuario@delAdmin.com','$2a$10$b9iEQKi1vRK6/YfFVPvTc.m7aQCp/ywpKMfOmxYbuI04gjFmp0EjC','','cliente',1),(47,'unUserMas','usuario','DelAdmin','usuario@delAdmin.com','$2a$10$y3HkX3YK6/l6DDjWeBZbg.nQramFk1kX71fxgDsvXcjc21/QQZbO6','','cliente',1),(48,'ccimino','Carlos','Cimino','correo@falso.com','$2a$10$dSO2kPSHcQcVzrp8FDOckO25bxHD9jKeMZYdg0WHyQGHgEYcaSZ66','','cliente',1),(49,'Xander_dick69','Xander','DelaCruz  E===D','dickdelacruz@fake.com','$2a$10$mRtjOKGJDY.TSB0b7n4mruSMQUSITnSpCj3uQJnt0IaHmXuRF5O7W','0800delruso','prestador',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gestion_domination'
--

--
-- Dumping routines for database 'gestion_domination'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-24 15:20:47
