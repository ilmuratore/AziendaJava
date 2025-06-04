Changelog – Versione 0.1.0

Domain Model
Create tutte le entità JPA (@Entity) con campi, relazioni e audit fields
Definite le Enum (Gender, ProjectStatus, TaskStatus, ecc.)
Persistence Layer
Sviluppati i repository Spring Data JPA (CrudRepository / JpaRepository) per ogni Entity
DTO Layer
Implementati i DTO per tutte le entità, con campi base e riferimenti tramite ID per le associazioni
Applicato Lombok (@Getter/@Setter/@NoArgsConstructor) per ridurre boilerplate
Mapping Layer
Creata un’interfaccia EntityMapper con MapStruct (componentModel = "spring")
Metodi toDto() e toEntity per tutte le entità + helper toIdSet() per convertire collezioni di entità in set di ID
Error Handling
Definite exception custom (es. EntityNotFoundException, ValidationException)

Changelog – Versione 0.2.0 (In sviluppo)

Definizione delle interfacce di servizio (Service/ServiceImpl) con metodi CRUD tutti tipizzati con DTO
Implementazione dei controller REST (@RestController) e dei contratti API (endpoints CRUD per ogni risorsa)
Implementazione Swagger e JavaDoc in duplice lingua (Eng/Ita)
Implementazione JavaFX per implementare GUI grafica per utilizzo in locale
Implementazione plugin e utility per migliorare la vita del Dev (non ringraziarmi)
Implementato SetupWizard con CLI di comando in caso di esecuzione del software al primo avvio e altre funzioni
Implementate dipendenze e pacchetti JavaFX (al momento compatibile solo con Windows)


