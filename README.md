# Dokumentasi Proyek Spring Boot & Vue.js - Online Library

Berikut adalah **dokumentasi lengkap** untuk menjalankan aplikasi Spring Boot, mengimpor data SQL, dan dokumentasi API yang disediakan oleh aplikasi **Online Library**.

## Diagram Entity-Relationship
![onlinelibrary](https://github.com/user-attachments/assets/b50d533e-1f7a-4103-ad63-97da3a85f6c5)

## 1. Clone Repository


Clone repository proyek dari Git ke local machine Anda.

```bash
git clone https://github.com/yusup-dev/online-library.git
```

## 2. Extrak File

Jika Anda telah mengunduh ZIP file, ekstrak file tersebut ke folder lokal menggunakan perintah berikut, atau ekstrak secara manual.

```bash
unzip online-library.zip
```

## 3. Setup MySQL Database

a. **Aktifkan MySQL Server**
Pastikan server MySQL Anda sudah berjalan. Jika belum aktif, gunakan perintah ini untuk mengaktifkannya.

* Untuk Windows, cari "MySQL" dan klik Start MySQL Server dari MySQL Workbench atau XAMPP.
* Untuk Linux/Unix:
  ```bash
  sudo service mysql start
  ```

b. **Buat Database Baru**
Buat database baru yang akan digunakan oleh aplikasi.
```bash
mysql -u root -p
CREATE DATABASE onlinelibrary;
```
Gantilah `onlinelibrary` jika Anda menggunakan nama database lain.

## 4. Konfigurasi Aplikasi (application.properties)
Arahkan ke file `src/main/resources/application.properties`, dan konfigurasi kredensial database sesuai dengan pengaturan lokal Anda.
```bash
spring.application.name=onlinelibrary
spring.jpa.hibernate.ddl-auto=update
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/onlinelibrary
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
app.jwt-secret=38a0963a6364b09ad867aa9a66c6d009673c21e182015461da236ec361877f77
app-jwt-expiration-milliseconds=604800000
```
**Catatan**: Pastikan `spring.datasource.username` dan `spring.datasource.password` sesuai dengan kredensial MySQL di mesin Anda.

## 5. Install Dependencies
Instal dependensi Maven yang diperlukan dengan menjalankan perintah berikut di root folder proyek.
```bash
mvn clean install
```
Ini akan mengunduh semua library yang dibutuhkan dan melakukan kompilasi.

## 6. Jalankan Aplikasi
Untuk menjalankan aplikasi Spring Boot, gunakan perintah berikut:
```bash
mvn spring-boot:run
```
Aplikasi sekarang akan berjalan di port default 8080. Anda bisa mengakses aplikasi di browser melalui:
```bash
http://localhost:8080
```

## 7. SQL Dump untuk Mengimpor Data
Untuk kemudahan, Anda bisa mengimpor data awal (contoh buku, pengguna, peran, dll.) ke dalam database menggunakan file SQL dump yang disediakan.
a. **File SQL Dump
Simpan file SQL dump berikut sebagai `initial_data.sql` di folder root proyek Anda:
```bash
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
(1,'2024-10-23 00:11:51.180597','2024-10-23 07:35:30.302382','John Doe',_binary '','The Java Handbook','https://d1m4wul6rdhiz0.cloudfront.net/wp-content/uploads/2023/09/showtime-PhotoRoom-1-1.jpg'),
(2,'2024-10-23 00:19:31.389580','2024-10-23 07:30:48.987527','John OE',_binary '\0','The Programing','https://i.ibb.co.com/QbZQjGc/images-1.jpg'),
(3,'2024-10-23 00:20:57.517415','2024-10-23 07:33:49.212933','John OE',_binary '\0','Data Structure','https://i.ibb.co.com/gS63Nvf/images.jpg'),
(4,'2024-10-23 00:21:43.887165','2024-10-23 07:34:34.970813','John OE',_binary '\0','Design Pattern','https://i.ibb.co.com/xswzZNM/download-1.jpg'),
(6,'2024-10-23 00:24:10.865843',NULL,'John OE',_binary '','Object Oriented Programming','https://i.ibb.co.com/37rZxDz/download.jpg');

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
```
Catatan : Untuk value table **role** dan **user dengan role admin** harus insert langsung didatabase nya:
```bash
INSERT INTO `roles` VALUES 
(1,'2024-10-22 22:06:57.000000','2024-10-22 22:07:01.000000','ROLE_USER'),
(2,'2024-10-23 00:07:33.000000','2024-10-23 00:07:36.000000','ROLE_ADMIN');

INSERT INTO `users` VALUES 
(1,'2024-10-23 00:09:02.000000','2024-10-23 00:09:07.000000','admin@gmail.com','Admin','$2a$10$Ndnur1kRXBcLc74hcA06cutKkMKNN3sILazORIlsFz6wcxVuueSp.'),
```
b. **Mengimpor SQL Dump**
Setelah menyimpan file dump, gunakan perintah berikut untuk mengimpornya ke database:
```bash
mysql -u root -p onlinelibrary < initial_data.sql
```

## 8. Dokumentasi API
Aplikasi ini menyediakan beberapa endpoint REST API untuk mengelola buku, pengguna, dan pinjaman. Berikut adalah daftar endpoint beserta penjelasannya:
a. **Books API**
* Get All Books
  * **Endpoint**: `GET /api/books`
  * **Otorisasi**: ADMIN & USER
  * **Deskripsi**: Mengambil semua buku yang tersedia di perpustakaan
  * **Contoh**
    ```bash
    curl -X GET http://localhost:8080/api/books
    ```
  * **Response** (200 OK):
    ```bash
    {
      "id": 1,
      "imageUrl": "https://i.ibb.co.com/37rZxDz/download.jpg",
      "title": "Object Oriented Programming",
      "author": "John OE",
      "available": true
    },
    ...
    ```
* Get Book by ID
  * **Endpoint**: `GET /api/books/{id}`
  * **Otorisasi**: ADMIN & USER
  * **Deskripsi**: Mengambil detail buku berdasarkan ID.
  * **Contoh**
    ```bash
    curl -X GET http://localhost:8080/api/books/1
    ```
  * **Response** (200 OK):
    ```bash
    {
      "id": 1,
      "imageUrl": "https://i.ibb.co.com/QbZQjGc/images-1.jpg",
      "title": "The Programing",
      "author": "John OE",
      "available": true
    }
    ```
* Get Book by User Loan
  * **Endpoint**: `GET /api/books/user/{idUser}/loans`
  * **Otorisasi**: USER
  * **Deskripsi**: Mengambil detail buku berdasarkan user yang meminjam buku.
  * **Contoh**
    ```bash
    curl -X GET /api/books/user/{idUser}/loans
    ```
  * **Response** (200 OK):
    ```bash
    {
      "id": 1,
      "imageUrl": "https://i.ibb.co.com/QbZQjGc/images-1.jpg",
      "title": "The Programing",
      "author": "John OE",
      "available": false
    }
    ```
* Add a New Book
  * **Endpoint**: `POST /api/books`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Menambahkan buku baru ke perpustakaan.
  * **Body**
    ```bash
    {
      "imageUrl": "https://i.ibb.co.com/37rZxDz/download.jpg",
      "title": "Object Oriented Programming",
      "author": "John OE",
      "available": true
    }
    ```
  * **Contoh**
    ```bash
    curl -X POST http://localhost:8080/api/books \
    -H "Content-Type: application/json" \
    -d '{"image_url": "https://i.ibb.co.com/37rZxDz/download.jpg", "title": "The Java Handbook","author": "John OE", "available": true}'
    ```
* Update a Book
  * **Endpoint**: `PUT /api/books/{id}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Memperbarui data buku berdasarkan ID.
  * **Body**
    ```bash
    {
      "imageUrl": "https://i.ibb.co.com/37rZxDz/download.jpg",
      "title": "Object Oriented Programming",
      "author": "John OE",
      "available": true
      }
    ```
  * **Contoh**
    ```bash
    curl -X PUT http://localhost:8080/api/books/1 \
    -H "Content-Type: application/json" \
    -d '{"image_url": "https://i.ibb.co.com/37rZxDz/download.jpg", "title": "The Java Handbook (Updated)","author": "John OE", "available": false}'
    ```
* Delete a Book
  * **Endpoint**: `DELETE /api/books/{id}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Menghapus buku berdasarkan ID.
  * **Contoh**
    ```bash
    curl -X DELETE http://localhost:8080/api/books/1
    ```
b. **Authentication API**
* Login
  * **Endpoint**: `POST /api/auth/login`
  * **Deskripsi**: Mendapatkan token JWT untuk autentikasi pengguna.
  * **Otorisasi**: ADMIN dan USER
  * **Body**
    ```bash
    {
      "email": "admin@gmail.com",
      "password": "Password123"
    }
    ```
  * **Contoh**
    ```bash
    curl -X POST http://localhost:8080/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{"email": "admin@gmail.com", "password": "Password123"}'
    ```
  * **Response** (200 OK):
    ```bash
    {
      "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3Mjk2NDEwOTUsImV4cCI6MTczMDI0NTg5NX0.dY3yN8OaVTgV5QSJN0bzsB6wG4L9mtpd8GF0tIoxv6jH-BGFZz-oOlHJ3g9PuPw5",
      "tokenType": "Bearer",
      "role": "ROLE_ADMIN",
      "userId": 6,
    }
    ```
* Register
  * **Endpoint**: `POST /api/auth/Register`
  * **Deskripsi**: Daftar sebagai pengguna.
  * **Otorisasi**: USER
  * **Body**
    ```bash
    {
      "name": "Yusup"
      "email": "yusup@gmail.com",
      "password": "Password123"
    }
    ```
  * **Contoh**
    ```bash
    curl -X POST http://localhost:8080/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{"name": "Yusup", "email": "yusup@gmail.com", "password": "Password123"}'
    ```
  * **Response** (200 OK):
    ```bash
    User registered successfully!.
    ```
c. **Loans API**
* Get All Loans
  * **Endpoint**: `POST GET /api/loans`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Mengambil daftar semua pinjaman.
  * **Contoh**
    ```bash
    curl -X GET http://localhost:8080/api/loans
    ```
  * **Response** (200 OK):
    ```bash
    [
      {
          "id": 1,
          "userId": 11,
          "bookId": 3,
          "borrowedAt": [2024,10,27,16,45,6,627719000],
          "returnDate": [2024,10,27,17,35,29,728444000
          ],
          "user": {
              "name": "Muhamad Yusup",
              "email": "yusup@gmail.com"
          },
          "book": {
              "id": 3,
              "imageUrl": "https://i.ibb.co.com/gS63Nvf/images.jpg",
              "title": "Data Structure",
              "author": "John OE",
              "available": true
          }
      },
      ...
    }
    ```
* Get Loan by ID
  * **Endpoint**: `POST GET GET /api/loans/{id}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Mengambil detail pinjaman berdasarkan ID..
  * **Parameter**:
    - `id`: ID pinjaman (Long, required)
  * **Contoh**
    ```bash
    curl -X GET http://localhost:8080/api/loans/1
    ```
  * **Response** (200 OK):
    ```bash
    {
      "id": 1,
      "userId": 2,
      "bookId": 2,
      "borrowedAt": [2024,10,23,0,47,4,428308000],
      "returnDate": [2024,10,23,1,19,48,939721000]
    }
    ```
* Add a New Loan
  * **Endpoint**: `POST POST /api/loans`
  * **Otorisasi**: USER
  * **Deskripsi**: Membuat pinjaman baru untuk seorang pengguna dan buku tertentu.
  * **Parameter**:
    - `userId`: ID pengguna (Long, required)
    - `bookId`: ID buku (Long, required)
  * **Contoh**
    ```bash
    curl -X POST "http://localhost:8080/api/loans?userId=1&bookId=1"
    ```
* Update a Loan
  * **Endpoint**: `PUT /api/books/{id}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Memperbarui detail pinjaman berdasarkan ID.
  * **Parameter**:
    - `id`: ID pinjaman (Long, required)
  * **Body**
    ```bash
    {
      "borrowedAt": [2024,10,23,0,47,4,428308000],
      "returnDate": [2024,10,23,1,19,48,939721000]
    }
    ```
  * **Contoh**
    ```bash
      curl -X PUT http://localhost:8080/api/loans/1 -H "Content-Type: application/json" -d '{"borrowedAt": "2024-10-23T12:00:00", "returnDate": "2024-11-23T12:00:00"}'
    ```
  * **Response** (200 OK):
    ```bash
    {
      "id": 1,
      "userId": 2,
      "bookId": 2,
      "borrowedAt": [2024,10,23,0,47,4,428308000],
      "returnDate": [2024,10,23,1,19,48,939721000]
    }
    ```
* Delete a Loan
  * **Endpoint**: `DELETE /api/loans/{id}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Menghapus pinjaman berdasarkan ID.
  * **Contoh**
    ```bash
    curl -X DELETE http://localhost:8080/api/loans/1
    ```
  * **Response** (200 OK):
    ```bash
    Loan deleted successfully!.
    ```
* Return a Book
  * **Endpoint**: `POST /api/loans/return?userId={id}`
  * **Otorisasi**: USER
  * **Deskripsi**: Mengembalikan buku berdasarkan ID pengguna.
  * **Parameter**:
    - `userId`: ID pengguna (Long, required)
  * **Contoh**
    ```bash
    curl -X POST "http://localhost:8080/api/loans/return?userId=1"
    ```
* Check if Book is Overdue
  * **Endpoint**: `GET /api/loans/overdue/{loanId}`
  * **Otorisasi**: ADMIN
  * **Deskripsi**: Memeriksa apakah pinjaman buku sudah jatuh tempo..
  * **Parameter**:
    - `loanId`: ID pinjaman  (Long, required)
  * **Contoh**
    ```bash
    curl -X GET http://localhost:8080/api/loans/overdue/1
    ```
  * **Response** (200 OK):
    ```bash
    {
      "overDue": false
    }
    ```
## 9. Menjalankan Frontend Vue.js
* Persiapan Awal
  Pastikan Anda telah menginstal Node.js dan npm di komputer Anda. Anda dapat memeriksa instalasi dengan perintah berikut:
  ```bash
  node -v
  npm -v
  ```
* Navigasi ke Folder Frontend
  Setelah clone repository berhasil, navigasikan ke folder `frontend` di dalam proyek `onlinelibrary`
  ```bash
  cd frontend
  ```
* Install Dependencies
  Install semua dependencies yang diperlukan untuk Vue.js dengan perintah berikut:
  ```bash
  npm install
  ```
* Menjalankan Proyek
  ```bash
  npm run dev
  ```
## 10. Design Web (UI)
* Halaman Register

  ![Screenshot 2024-10-27 184521](https://github.com/user-attachments/assets/7f4d7041-4345-4a3d-83ae-09da0580fced)

* Halaman Login
  
  ![Screenshot 2024-10-27 184505](https://github.com/user-attachments/assets/0af570b6-1e52-4839-88ec-9c2afe26daa5)

* Halaman User

  ![Screenshot 2024-10-27 184613](https://github.com/user-attachments/assets/9e9aca5c-101a-4944-b3af-7a5c99ca8984)

* Halaman Detail Buku

  ![Screenshot 2024-10-27 184645](https://github.com/user-attachments/assets/fdcca79d-fd04-4620-899e-76971f6bfb86)

* Halaman Admin

  ![Screenshot 2024-10-27 184536](https://github.com/user-attachments/assets/a92d1858-6a33-4a52-a0d1-348d7137c70f)

* Halama Detail Peminjaman
  
  ![Screenshot 2024-10-27 185446](https://github.com/user-attachments/assets/9ecec77a-1afd-47f3-a73e-e2947a601414)


















