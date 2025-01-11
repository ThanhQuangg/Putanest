-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: putanest
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `cartdetails`
--

DROP TABLE IF EXISTS `cartdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetails` (
  `CartDetailID` int NOT NULL AUTO_INCREMENT,
  `CartID` int NOT NULL,
  `ProductID` int NOT NULL,
  `Quantity` int NOT NULL DEFAULT '1',
  `Price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`CartDetailID`),
  KEY `CartID` (`CartID`),
  KEY `ProductID` (`ProductID`),
  CONSTRAINT `cartdetails_ibfk_1` FOREIGN KEY (`CartID`) REFERENCES `carts` (`CartID`) ON DELETE CASCADE,
  CONSTRAINT `cartdetails_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetails`
--

LOCK TABLES `cartdetails` WRITE;
/*!40000 ALTER TABLE `cartdetails` DISABLE KEYS */;
INSERT INTO `cartdetails` VALUES (4,2,21,5,55000.00),(5,3,22,5,55000.00);
/*!40000 ALTER TABLE `cartdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `CartID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CartID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,1,'2024-12-04 10:44:20'),(2,5,'2024-12-12 15:48:02'),(3,6,'2024-12-12 16:45:10'),(4,7,'2024-12-13 11:53:34'),(5,8,'2024-12-13 11:56:42'),(6,9,'2024-12-14 10:40:07');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CategoryID`),
  UNIQUE KEY `CategoryName` (`CategoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Yến hủ thủy tinh','Yến được chưng trong hũ thủy tinh sang trọng, có thể dùng trực tiếp. Phù hơp để sử dụng nâng cao sức khỏe hoặc làm quà tặng cho người thân',NULL,'2024-12-11 14:02:29'),(4,'Yến tổ','Yến được chưng trong hũ thủy tinh sang trọng, có thể dùng trực tiếp. Phù hơp để sử dụng nâng cao sức khỏe hoặc làm quà tặng cho người thân','2024-12-04 09:59:09','2024-12-04 09:59:09'),(7,'A','AB',NULL,'2025-01-11 15:59:49');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetails` (
  `OrderDetailID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `ProductID` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`OrderDetailID`),
  KEY `OrderID` (`OrderID`),
  KEY `ProductID` (`ProductID`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`) ON DELETE CASCADE,
  CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` VALUES (5,3,23,5,45000.00),(6,3,27,7,50000.00),(7,4,22,5,55000.00),(8,5,22,5,55000.00),(9,6,22,5,55000.00),(10,7,22,5,55000.00),(11,8,22,5,55000.00);
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `TotalAmount` decimal(38,2) DEFAULT NULL,
  `OrderStatus` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`OrderID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,95000.00,'Pending','2024-12-04 13:45:39','2024-12-04 13:45:39'),(2,2,575000.00,'Pending','2024-12-04 13:49:50','2024-12-04 13:49:50'),(3,7,575000.00,'Pending','2024-12-16 15:34:18','2024-12-16 15:34:18'),(4,6,275000.00,'Pending','2024-12-16 15:51:24','2024-12-16 15:51:24'),(5,6,275000.00,'Pending','2024-12-16 16:19:38','2024-12-16 16:19:38'),(6,6,275000.00,'Pending','2024-12-16 16:22:08','2024-12-16 16:22:08'),(7,6,275000.00,'Pending','2024-12-16 16:32:10','2024-12-16 16:32:10'),(8,6,275000.00,'Pending','2024-12-16 16:32:43','2024-12-16 16:32:43');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `ProductID` int NOT NULL AUTO_INCREMENT,
  `CategoryID` int NOT NULL,
  `ProductName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Description` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Price` decimal(38,2) DEFAULT NULL,
  `Quantity` int NOT NULL DEFAULT '0',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ProductID`),
  KEY `CategoryID` (`CategoryID`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (21,1,'Yến Chưng Thượng Hạng Đông Trùng Hạ Thảo 150ml 35%','Yến Chưng Thượng Hạng Đông Trùng Hạ Thảo 150ml 35% là sản phẩm dinh dưỡng cao cấp, kết hợp hoàn hảo giữa tổ yến nguyên chất và đông trùng hạ thảo quý giá. Với hàm lượng dinh dưỡng phong phú, sản phẩm không chỉ giúp bồi bổ sức khỏe, tăng cường sức đề kháng mà còn hỗ trợ nâng cao khả năng tập trung và giảm stress.',80000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733901922/hnddq422igfwikx8f97v.jpg','2024-12-11 14:23:04','2024-12-12 09:47:14'),(22,1,'Yến Chưng Thượng Hạng Đường Kiêng Đông Trùng 150ml 35%','Yến Chưng Thượng Hạng Đường Kiêng Đông Trùng 150ml 35% là sản phẩm dinh dưỡng độc đáo, kết hợp tổ yến nguyên chất với đông trùng hạ thảo và đường kiêng, hoàn hảo cho những ai muốn kiểm soát lượng đường nhưng vẫn tận hưởng hương vị thơm ngon. Với hàm lượng dinh dưỡng cao, sản phẩm không chỉ hỗ trợ sức khỏe tổng thể mà còn giúp tăng cường sức đề kháng và cải thiện tinh thần.',80000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733901837/pr3yfb2v2xhscahir60c.jpg','2024-12-11 14:23:58','2024-12-11 14:23:58'),(23,1,'Yến Chưng Thượng Hạng Đường Phèn 150ml 35%','Yến Chưng Thượng Hạng Đường Phèn 150ml 35% là món ăn bổ dưỡng, được chế biến từ tổ yến nguyên chất và đường phèn tự nhiên, mang đến hương vị thanh ngọt dễ chịu. Sản phẩm không chỉ cung cấp protein và các dưỡng chất quý giá, mà còn giúp thanh nhiệt, giải độc, phù hợp cho mọi lứa tuổi. Với quy trình chế biến tinh tế, yến chưng đường phèn 35% giữ nguyên được hương vị thơm ngon và giá trị dinh dưỡng, là lựa chọn lý tưởng cho sức khỏe và sắc đẹp.',80000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733901877/cesci5e3poocxn9ibzx9.jpg','2024-12-11 14:24:38','2024-12-11 14:24:38'),(24,1,'Yến Chưng Thượng Hạng Saffron 35%','Yến chưng Thượng Hạng Saffron 35% là sản phẩm dinh dưỡng độc đáo, kết hợp tổ yến nguyên chất với saffron cao cấp, mang lại hương vị đặc biệt và nhiều lợi ích sức khỏe. Với hàm lượng dinh dưỡng phong phú, sản phẩm không chỉ giúp bồi bổ cơ thể mà còn hỗ trợ làm đẹp da và nâng cao tinh thần. Yến chưng saffron 35% là lựa chọn hoàn hảo cho những ai muốn tận hưởng một món ăn bổ dưỡng, thơm ngon và sang trọng, phù hợp cho mọi lứa tuổi.',40000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902018/t4suofpvzhb6ehwe1bhj.jpg','2024-12-11 14:26:58','2024-12-11 14:26:58'),(25,1,'Yến Chưng Thượng Hạng Kids Plus 35%','Yến Chưng Thượng Hạng Kids Plus 35% là sản phẩm dinh dưỡng tuyệt vời, được chiết xuất từ tổ yến chất lượng cao, giàu protein và các vitamin cần thiết cho sự phát triển của trẻ. Với hương vị thơm ngon, dễ uống, yến chưng không chỉ giúp tăng cường sức đề kháng mà còn hỗ trợ hệ tiêu hóa cho trẻ nhỏ. Sản phẩm an toàn và lành tính, là sự lựa chọn hoàn hảo cho bữa ăn phụ của bé yêu, giúp bé phát triển khỏe mạnh và thông minh.',35000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902069/qcfnix9qwjwzmwwpshjd.jpg','2024-12-11 14:27:49','2024-12-11 14:27:49'),(26,1,'Yến Chưng Thượng Hạng Đường Phèn 35%','Yến Chưng Thượng Hạng Đường Phèn 35% là món ăn bổ dưỡng, được chế biến từ tổ yến nguyên chất và đường phèn tự nhiên, mang đến hương vị thanh ngọt dễ chịu. Sản phẩm không chỉ cung cấp protein và các dưỡng chất quý giá, mà còn giúp thanh nhiệt, giải độc, phù hợp cho mọi lứa tuổi. Với quy trình chế biến tinh tế, yến chưng đường phèn 35% giữ nguyên được hương vị thơm ngon và giá trị dinh dưỡng, là lựa chọn lý tưởng cho sức khỏe và sắc đẹp.',35000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902105/dwmfdfa6307tpzhat9iz.jpg','2024-12-11 14:28:26','2024-12-11 14:28:26'),(27,1,'Yến Chưng Thượng Hạng Đường Ăn Kiêng 35%','Yến Chưng Thượng Hạng Đường Ăn Kiêng 35% là sản phẩm dinh dưỡng lý tưởng cho những ai muốn duy trì vóc dáng mà vẫn đảm bảo sức khỏe. Được chế biến từ tổ yến nguyên chất và đường ăn kiêng, sản phẩm mang đến hương vị thơm ngon mà không lo ngại về lượng calo. Với hàm lượng protein và vitamin cao, yến chưng đường ăn kiêng 35% không chỉ giúp bồi bổ cơ thể mà còn hỗ trợ quá trình giảm cân hiệu quả.',35000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902154/m4ao0dhi3aeq2onfzbqp.jpg','2024-12-11 14:29:14','2024-12-11 14:29:14'),(28,1,'Yến Chưng Thượng Hạng Đường Kiêng Đông Trùng 35%','Yến Chưng Thượng Hạng Đường Kiêng Đông Trùng 35% là sản phẩm dinh dưỡng độc đáo, kết hợp tổ yến nguyên chất với đông trùng hạ thảo và đường kiêng, hoàn hảo cho những ai muốn kiểm soát lượng đường nhưng vẫn tận hưởng hương vị thơm ngon. Với hàm lượng dinh dưỡng phong phú, sản phẩm không chỉ hỗ trợ sức khỏe tổng thể mà còn giúp tăng cường sức đề kháng và cải thiện tinh thần.',40000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902193/j3yp7bxd4kpkmwjafpyu.jpg','2024-12-11 14:29:53','2024-12-11 14:29:53'),(29,1,'Yến Chưng Thượng Hạng Đông Trùng Hạ Thảo 35%','Yến Chưng Thượng Hạng Đông Trùng Hạ Thảo 35% là sản phẩm dinh dưỡng cao cấp, kết hợp hoàn hảo giữa tổ yến nguyên chất và đông trùng hạ thảo quý giá. Với hàm lượng dinh dưỡng phong phú, sản phẩm không chỉ giúp bồi bổ sức khỏe, tăng cường sức đề kháng mà còn hỗ trợ nâng cao khả năng tập trung và giảm stress.',40000.00,200,'https://res.cloudinary.com/diimfetua/image/upload/v1733902227/vophripmehn437rjlpue.jpg','2024-12-11 14:30:28','2024-12-11 14:30:28');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `FullName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PhoneNumber` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CreatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Huyen Trang ','trangle@gmail.com','123','123456789','ABC','ROLE_Admin',NULL,'2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733283187/l2w6lmh3n1fczqrza8df.jpg','trang'),(2,'Huyen Trang 1','trangle1@gmail.com','123','123456789','ABC','ROLE_Admin',NULL,'2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733283175/f7qrwbvzhceatnbnumux.jpg','trang1'),(3,'Huyen Trang 2','trangle2@gmail.com','123','123456789','ABC','ROLE_Admin',NULL,'2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733283139/vi1cvodihpqnyx6hoprz.jpg','trang2'),(4,'Huyen Trang 3','trangle3@gmail.com','$2a$10$eYTSCuc/cFt0skn4vLqkU.t3b5BGF2pCFpu74jJXwbsq3eQ9k2VMS','123456789','ABC','ROLE_Admin','2024-12-12 15:28:23','2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733992104/hfg0jwrtqsy1oc2yu1if.jpg','trang3'),(5,'Huyen Trang 4','trangle4@gmail.com','$2a$10$JkbeBRNd3c7jVnRFyhQOOOcrmSOTxE6HW6d7vrUbBpHgtIuQvQmze','123456789','ABC','ROLE_Admin','2024-12-12 15:48:01','2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733993282/pvtnwz2lgv6ijdgrzsly.jpg','trang4'),(6,'Thành Quangg','thanhquang@gmail.com','$2a$10$GB7WIhzFZZQNY9Ldb1yMoOZ/kCFh8HiI38NKNZAaJYWH4xXkzIrq6',NULL,NULL,NULL,'2024-12-12 16:45:10','2024-12-13 10:09:42','https://res.cloudinary.com/diimfetua/image/upload/v1733996711/wd45p4ndkefu9hcvus0d.png','quang'),(7,'Quang Nguyễn','thanhquang1@gmail.com','$2a$10$YglshEnSU5ma6hGrfVfcj..UaxgOcrncLqVN2uIhtCZ8M/SfY5TEW','','','ROLE_Admin','2024-12-13 11:53:34','2024-12-13 11:53:34','https://res.cloudinary.com/diimfetua/image/upload/v1734065613/q6nvwprawe2lkm5oxbuj.jpg','thanhquang'),(8,'Quang Nguyễn 1','thanhquang2@gmail.com','$2a$10$a6KgDHEEjhJ2hb5fLvdN2.hzdN3el4DWDbs6ILa9WgGTntW0l0PTC','0123456789','205 Phạm Ngũ Lão , phường Phạm Ngũ Lão Quận 1, TPHCM','ROLE_Admin','2024-12-13 11:56:42','2024-12-13 11:56:42','https://res.cloudinary.com/diimfetua/image/upload/v1734065801/wkykca9lntbkkt4l0gu6.jpg','thanhquang1'),(9,'Lê Trang','trang@gmail.com','$2a$10$A3jJbAaMGeUvCYs6USuDz.gP0d7lhFgXcLb56WPtvD.0k6AGlLO4K','1561311811','ABC','ROLE_Admin','2024-12-14 10:40:07','2024-12-14 10:40:07','https://res.cloudinary.com/diimfetua/image/upload/v1734147606/bu6zbnbygdfuqunnvjfr.jpg','letrang');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-11 16:07:58
