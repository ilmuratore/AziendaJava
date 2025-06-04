# Employee Management System / Sistema di Gestione Dipendenti

<p align="center">
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen" alt="Spring Boot"></a>
<a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-3.8.5-blue" alt="Maven"></a>
<a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-21-orange" alt="Java"></a>
<a href="https://mariadb.org/"><img src="https://img.shields.io/badge/MariaDB-10.6-blue" alt="MariaDB"></a>
<a href="https://www.hibernate.org/"><img src="https://img.shields.io/badge/Hibernate-5.6-red" alt="Hibernate"></a>
<a href="https://projectlombok.org/"><img src="https://img.shields.io/badge/Lombok-enabled-lightgrey" alt="Lombok"></a>
<a href="https://openjfx.io/"><img src="https://img.shields.io/badge/JavaFX-21.0.6-teal" alt="JavaFX"></a>
<a href="https://swagger.io/"><img src="https://img.shields.io/badge/Swagger%20UI-5.22.0-yellow" alt="Swagger UI"></a>
<a href="./docs"><img src="https://img.shields.io/badge/Javadoc-generated-blueviolet" alt="Javadoc"></a>
  
</p>

---

## 📖 Descrizione / Description

**🇮🇹**  
Sistema di gestione dipendenti basato su Spring Boot 3.x e Java 17 che consente di creare, modificare e visualizzare record anagrafici dei dipendenti. Ogni dipendente è associato a un account con ruoli/permessi gestiti tramite JWT e Spring Security. Le password sono cifrate in database per garantire la massima sicurezza.

**🇬🇧**  
Spring Boot 3.x & Java 17-based employee management system enabling creation, updating and viewing of personal data records for employees linked to user accounts with roles and permissions. Passwords are securely encrypted in the database via Spring Security and JWT.

---

## 🚀 Caratteristiche / Features

| 🇮🇹 Italiano                                                            | 🇬🇧 English                                                   |
| ------------------------------------------------------------------------ | ------------------------------------------------------------- |
| ✅ CRUD completo per entità: dipendenti, ruoli e account                  | ✅ Full CRUD for entities: employees, roles, and accounts     |
| 🔐 Gestione account, ruoli e permessi con Spring Security + JWT           | 🔐 Account, role & permission management with Spring Security + JWT |
| 🗄️ Persistenza dati su MariaDB + JPA/Hibernate                             | 🗄️ Persistence using MariaDB + JPA/Hibernate                  |
| 📦 Struttura modulare con Maven                                           | 📦 Modular Maven project structure                             |
| 💡 Integrazione di Lombok per ridurre il codice boilerplate                | 💡 Lombok integration to cut boilerplate code                  |
| ⚙️ Configurazione automatica tramite wizard CLI                            | ⚙️ Automatic setup via CLI wizard                                |
| 🛠️ Generazione automatica di dati di test                                  | 🛠️ Automatic generation of sample data                          |
| 🔄 Reset rapido del database                                                | 🔄 Fast database reset                                          |
| 📄 Documentazione API con Swagger/OpenAPI                                  | 📄 API documentation via Swagger/OpenAPI                        |

---

## ⚙️ Tech Stack

- **Framework Backend**: Spring Boot 3.5 (Web, Security, Data JPA, Validation)  
- **Database**: MariaDB 10.x (produzione) / H2 (sviluppo)  
- **ORM**: Hibernate  
- **Utility**: Lombok, JavaFX  
- **Gestione progetti**: Maven   
- **Linguaggio**: Java 21  

---

## 🔧 Prerequisiti / Prerequisites

- Java 21+  
- Maven 3.6+  
- MariaDB server in esecuzione (o H2 per sviluppo)  
- (Facoltativo) Docker, se si preferisce eseguire MariaDB in contenitore  

---

## 📥 Installazione / Installation

1. **Clona il repository**  
   ```bash
   git clone https://github.com/ilmuratore/AziendaJava.git
   cd AziendaJava
2. **Configura le proprietà**
    Apri src/main/resources/application.properties e verifica i parametri di connessione a MariaDB:
    ### Esempio di configurazione per MariaDB
    spring.datasource.url=jdbc:mariadb://localhost:3306/azienda_db
    spring.datasource.username=tuo_utente
    spring.datasource.password=tua_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ### Esempio di configurazione per H2 (sviluppo)
    spring.datasource.url=jdbc:h2:mem:azienda_db;DB_CLOSE_DELAY=-1
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=create-drop
3. **Build e avvia l'applicazione**
    mvn clean install
    mvn spring-boot:run
    ### In alternativa, genera il jar e avvialo con:
    mvn clean package
    java -jar target/azienda-app.jar


## 🚀 Utilizzo CLI / CLI Commands
Avviare il jar con le seguenti opzioni (posiziona azienda-app.jar nella root del progetto o sostituisci con il percorso corretto):
1. **Avviare automaticamente il setup wizard**
    java -jar azienda-app.jar
2. **Setup manuale**
    java -jar azienda-app.jar --setup
    Permette di inserire manualmente i dati iniziali (es. tipologie di ruoli, utenti amministrativi, ecc.).
3. **Generare record di esempio**
    java -jar azienda-app.jar --generate-data 50
    Crea 50 dipendenti fittizi con ruoli e account, utili per testare l’interfaccia e le API.
4. **Reset del database**
    java -jar azienda-app.jar --reset
    Elimina tutte le tabelle e ricrea lo schema da zero (utile per un nuovo ciclo di sviluppo o test).
5. **Mostrare aiuto / Help**
java -jar azienda-app.jar --help

## 🔗 Collegamenti Utili / Useful Links (not working)
📄 Documentazione API (Swagger UI)
📁 Roadmap versione 3.0
📦 Esempi Postman Collection (coming soon)

## 📜 Licenza / License
MIT License
© 2025 Il Muratore (Simone Iengo)

## 👥 Contributori / Contributors
Simone Iengo – Full Stack Developer, manutentore principale 

