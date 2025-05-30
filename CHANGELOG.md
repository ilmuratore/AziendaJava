Changelog – Versione 0.1.0 (In sviluppo)

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

Metodi toDto() per tutte le entità + helper toIdSet() per convertire collezioni di entità in set di ID

(In cantiere: aggiungere metodi toEntity() e gestione delle associazioni inverse)

Error Handling

Definite exception custom (es. EntityNotFoundException, ValidationException)

Roadmap – Prossima release (v0.2.0)

Definizione delle interfacce di servizio (Service/ServiceImpl) con metodi CRUD tutti tipizzati con DTO

Implementazione dei controller REST (@RestController) e dei contratti API (endpoints CRUD per ogni risorsa)

