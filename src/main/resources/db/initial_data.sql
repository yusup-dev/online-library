DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author` varchar(265) NOT NULL,
  `available` bit(1) NOT NULL,
  `title` varchar(265) NOT NULL,
  `image_url` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `books` VALUES 
(1,'2024-10-23 00:11:51.180597','2024-10-23 07:35:30.302382','John Doe',_binary '�','The Java Handbook','https://d1m4wul6rdhiz0.cloudfront.net/wp-content/uploads/2023/09/showtime-PhotoRoom-1-1.jpg'),
(2,'2024-10-23 00:19:31.389580','2024-10-23 07:30:48.987527','John OE',_binary '\0','The Programing','https://i.ibb.co.com/QbZQjGc/images-1.jpg'),
(3,'2024-10-23 00:20:57.517415','2024-10-23 07:33:49.212933','John OE',_binary '\0','Data Structure','https://i.ibb.co.com/gS63Nvf/images.jpg'),
(4,'2024-10-23 00:21:43.887165','2024-10-23 07:34:34.970813','John OE',_binary '\0','Design Pattern','https://i.ibb.co.com/xswzZNM/download-1.jpg'),
(6,'2024-10-23 00:24:10.865843',NULL,'John OE',_binary '�','Object Oriented Programming','https://i.ibb.co.com/37rZxDz/download.jpg');

DROP TABLE IF EXISTS `loans`;
CREATE TABLE `loans` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `borrowed_at` datetime(6) NOT NULL,
  `return_date` datetime(6) DEFAULT NULL,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKokwvlrv6o4i4h3le3bwhe6kie` (`book_id`),
  KEY `FK6xxlcjc0rqtn5nq28vjnx5t9d` (`user_id`),
  CONSTRAINT `FK6xxlcjc0rqtn5nq28vjnx5t9d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKokwvlrv6o4i4h3le3bwhe6kie` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `loans` VALUES 
(1,'2024-10-23 07:30:48.870510','2024-10-23 07:32:45.461966','2024-10-23 07:30:48.729536','2024-10-23 07:30:48.729536',2,1),
(2,'2024-10-23 07:33:19.299051','2024-10-23 07:35:30.286820','2024-10-23 07:33:19.287983','2024-10-23 07:35:30.285826',1,2),
(3,'2024-10-23 07:33:49.200525','2024-10-23 07:36:53.247238','2024-10-23 07:30:48.729536','2024-10-23 07:30:48.729536',3,3),
(4,'2024-10-23 07:34:34.952195',NULL,'2024-10-23 07:34:34.939655',NULL,4,4);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(265) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles` VALUES 
(1,'2024-10-22 22:06:57.000000','2024-10-22 22:07:01.000000','ROLE_USER'),
(2,'2024-10-23 00:07:33.000000','2024-10-23 00:07:36.000000','ROLE_ADMIN');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(225) NOT NULL,
  `name` varchar(225) NOT NULL,
  `password` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` VALUES 
(1,'2024-10-22 22:10:24.395202','2024-10-23 00:09:05.000000','mhmdy5p0317@gmail.com','Yusup','$2a$10$Ndnur1kRXBcLc74hcA06cutKkMKNN3sILazORIlsFz6wcxVuueSp.'),
(2,'2024-10-23 00:09:02.000000','2024-10-23 00:09:07.000000','admin@gmail.com','Admin','$2a$10$Ndnur1kRXBcLc74hcA06cutKkMKNN3sILazORIlsFz6wcxVuueSp.'),
(3,'2024-10-23 00:46:33.957076',NULL,'user@gmail.com','User','$2a$10$CPNZrJNUapjiJfWyAAfDNeOGa0EsZwDbc.bg9U11xHR9JTwtWuUui'),
(4,'2024-10-23 04:01:32.184615',NULL,'muh.yusup965@gmail.com','Muhamad Yusup','$2a$10$dlJWlkg9ndN3tr1rUTtrAO5vSKbYmEeTjo7n3n5pu9SzmdNDBBF26'),
(5,'2024-10-23 04:05:54.420798',NULL,'muhyusup.my@gmail.com','Muhamad Yusup ','$2a$10$y212UMtZIDVhfkpjkR1XM.vmGqQt09Bi55LZ6UDnF5ShDUrN.1Cum');

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users_roles` VALUES 
(1,1),
(3,1),
(4,1),
(5,1),
(2,2);