# Employee Management System / Sistema di Gestione Dipendenti

<p align="center">
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-2.7.3-brightgreen" alt="Spring Boot"></a>
  <a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-3.8.5-blue" alt="Maven"></a>
  <a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-17-orange" alt="Java 17"></a>
  <a href="https://mariadb.org/"><img src="https://img.shields.io/badge/MariaDB-10.6-blue" alt="MariaDB"></a>
  <a href="https://www.hibernate.org/"><img src="https://img.shields.io/badge/Hibernate-5.6.9-red" alt="Hibernate"></a>
  <a href="https://projectlombok.org/"><img src="https://img.shields.io/badge/Lombok-enabled-lightgrey" alt="Lombok"></a>
</p>

---

## 📖 Descrizione / Description

**🇮🇹**  
Sistema di gestione dipendenti basato su Spring Boot che permette di inserire, modificare e visualizzare dati anagrafici dei dipendenti, associati a un account con permessi. Le password sono cifrate in database per garantire la sicurezza.

**🇬🇧**  
Spring Boot-based employee management system enabling creation, updating and viewing of personal data records for employees linked to user accounts with roles/permissions. Passwords are securely encrypted in the database.

---

## 🚀 Caratteristiche / Features

| 🇮🇹 Italiano                                     | 🇬🇧 English                                        |
| ------------------------------------------------ | -------------------------------------------------- |
| ✅ CRUD completo per i dipendenti                 | ✅ Full CRUD for employees                         |
| 🔐 Gestione account e ruoli con sicurezza         | 🔐 Account & role management with secured passwords|
| 🗄️ Persistenza con MariaDB + JPA/Hibernate        | 🗄️ Persistence using MariaDB + JPA/Hibernate       |
| 📦 Progetto modulare con Maven                    | 📦 Modular project structure with Maven            |
| 💡 Uso di Lombok per ridurre boilerplate          | 💡 Lombok integration to cut boilerplate           |

---

## ⚙️ Tech Stack

- **Spring Boot** (Web, Security, Data JPA)  
- **MariaDB** (10.x)  
- **Hibernate**  
- **Lombok**  
- **Maven**  
- **Java 17**

---

## 🔧 Prerequisiti / Prerequisites

- Java 17+  
- Maven 3.6+  
- MariaDB server in esecuzione

---

## 📥 Installazione / Installation


# Clona il repository
git clone https://github.com/ilmuratore/AziendaJava
cd AziendaJava

# Configura le proprietà in src/main/resources/application.properties


# Compila e avvia
mvn clean install
mvn spring-boot:run
