package com.example.demo.config.mapper;

import com.example.demo.dto.*;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Interfaccia MapStruct per la conversione tra entità e DTO relativi ad Account.
 *
 * <p><strong>English:</strong> MapStruct interface to map between entity and DTO objects related to Account.</p>
 * <p><strong>Italiano:</strong> Interfaccia MapStruct per mappare tra oggetti entità e DTO relativi ad Account.</p>
 *
 * <ul>
 *     <li>
 *         <strong>Methods:</strong>
 *         <ul>
 *             <li>{@link #toAccountResponseDto(Account)}: mappa da {@code Account} a {@code AccountResponseDTO}.</li>
 *             <li>{@link #toLoginResponseDto(Account, String)}: mappa da {@code Account} a {@code LoginResponseDTO} includendo un token JWT.</li>
 *             <li>{@link #toIdSet(Set)}: metodo helper per convertire un {@code Set<Entity>} in {@code Set<Long>} di soli ID.</li>
 *             <li>{@link #rolesToNameSet(Set)}: metodo helper per convertire un {@code Set<Role>} in {@code Set<String>} di nomi di ruolo.</li>
 *         </ul>
 *     </li>
 * </ul>
 */
@Mapper(componentModel = "spring")
public interface EntityMapper {

    /**
     * Converte un’entità {@code Account} in {@code AccountResponseDTO}.
     *
     * <p><strong>English:</strong> Maps an {@code Account} entity to an {@code AccountResponseDTO}.</p>
     * <p><strong>Italiano:</strong> Mappa un’entità {@code Account} in {@code AccountResponseDTO}.</p>
     *
     * <p>Campi mappati (entity → DTO):</p>
     * <ul>
     *     <li>{@code id} → {@code id}</li>
     *     <li>{@code username} → {@code username}</li>
     *     <li>{@code emailVerified} → {@code emailVerified}</li>
     *     <li>{@code enabled} → {@code enabled}</li>
     *     <li>{@code lastLogin} → {@code lastLogin}</li>
     *     <li>{@code failedAttempts} → {@code failedAttempts}</li>
     *     <li>{@code lockedUntil} → {@code lockedUntil}</li>
     *     <li>{@code createdAt} → {@code createdAt}</li>
     *     <li>{@code updatedAt} → {@code updatedAt}</li>
     *     <li>{@code persona.id} → {@code personaId}</li>
     *     <li>{@code roles} → {@code roleIds} (tramite {@link #toIdSet(Set)})</li>
     *     <li>{@code persona.firstName} → {@code personaFirstName}</li>
     *     <li>{@code persona.lastName} → {@code personaLastName}</li>
     *     <li>{@code roles} → {@code roleNames} (tramite {@link #rolesToNameSet(Set)})</li>
     * </ul>
     *
     * @param account l'oggetto {@code Account} da convertire
     *                <p><strong>English:</strong> the {@code Account} object to map from.</p>
     *                <p><strong>Italiano:</strong> l’oggetto {@code Account} da cui mappare.</p>
     * @return {@code AccountResponseDTO} popolato con i campi dell’entità {@code Account}
     *         <p><strong>English:</strong> an {@code AccountResponseDTO} populated with data from the {@code Account} entity.</p>
     *         <p><strong>Italiano:</strong> un {@code AccountResponseDTO} popolato con i dati dell’entità {@code Account}.</p>
     */
    @Mapping(target = "id",            source = "id")
    @Mapping(target = "username",      source = "username")
    @Mapping(target = "emailVerified", source = "emailVerified")
    @Mapping(target = "enabled",       source = "enabled")
    @Mapping(target = "lastLogin",     source = "lastLogin")
    @Mapping(target = "failedAttempts",source = "failedAttempts")
    @Mapping(target = "lockedUntil",   source = "lockedUntil")
    @Mapping(target = "createdAt",     source = "createdAt")
    @Mapping(target = "updatedAt",     source = "updatedAt")
    // Relazioni
    @Mapping(target = "personaId",     source = "persona.id")
    @Mapping(target = "roleIds",       source = "roles", qualifiedByName = "toIdSet")
    // Dati de normalizzati per frontend
    @Mapping(target = "personaFirstName", source = "persona.firstName")
    @Mapping(target = "personaLastName",  source = "persona.lastName")
    @Mapping(target = "roleNames",        source = "roles", qualifiedByName = "rolesToNameSet")
    AccountResponseDTO toAccountResponseDto(Account account);


    /**
     * Converte un’entità {@code Account} in {@code LoginResponseDTO}, includendo un token JWT.
     *
     * <p><strong>English:</strong> Maps an {@code Account} entity to a {@code LoginResponseDTO}, including a JWT token.</p>
     * <p><strong>Italiano:</strong> Mappa un’entità {@code Account} in {@code LoginResponseDTO}, includendo un token JWT.</p>
     *
     * <p>Campi mappati (entity → DTO):</p>
     * <ul>
     *     <li>{@code account.id} → {@code id}</li>
     *     <li>{@code account.username} → {@code username}</li>
     *     <li>{@code account.emailVerified} → {@code emailVerified}</li>
     *     <li>{@code account.enabled} → {@code enabled}</li>
     *     <li>{@code account.lastLogin} → {@code lastLogin}</li>
     *     <li>{@code account.roles} → {@code roleNames} (tramite {@link #rolesToNameSet(Set)})</li>
     *     <li>{@code token} → {@code token}</li>
     * </ul>
     *
     * @param account l'oggetto {@code Account} da convertire
     *                <p><strong>English:</strong> the {@code Account} object to map from.</p>
     *                <p><strong>Italiano:</strong> l’oggetto {@code Account} da cui mappare.</p>
     * @param token   il token JWT da includere nella risposta
     *                <p><strong>English:</strong> the JWT token to include in the response.</p>
     *                <p><strong>Italiano:</strong> il token JWT da includere nella risposta.</p>
     * @return {@code LoginResponseDTO} popolato con i campi dell’entità {@code Account} e il token JWT
     *         <p><strong>English:</strong> a {@code LoginResponseDTO} populated with account data and the JWT token.</p>
     *         <p><strong>Italiano:</strong> un {@code LoginResponseDTO} popolato con i dati dell’account e il token JWT.</p>
     */
    @Mapping(target = "id",            source = "account.id")
    @Mapping(target = "username",      source = "account.username")
    @Mapping(target = "emailVerified", source = "account.emailVerified")
    @Mapping(target = "enabled",       source = "account.enabled")
    @Mapping(target = "lastLogin",     source = "account.lastLogin")
    @Mapping(target = "roleNames",     source = "account.roles", qualifiedByName = "rolesToNameSet")
    LoginResponseDTO toLoginResponseDto(Account account, String token);

    /**
     * Converte un {@code Set<?>} di entità in un {@code Set<Long>} di ID,
     * utilizzando reflection per invocare il metodo {@code getId()} di ogni entità.
     *
     * <p><strong>English:</strong> Helper method to convert a {@code Set<?>} of entities into a {@code Set<Long>} of IDs,
     * using reflection to invoke {@code getId()} on each entity object.</p>
     * <p><strong>Italiano:</strong> Metodo helper per convertire un {@code Set<?>} di entità in un {@code Set<Long>} di ID,
     * utilizzando reflection per invocare {@code getId()} su ciascun oggetto entità.</p>
     *
     * <p>Se il set è {@code null} o vuoto, restituisce {@code null}.</p>
     * <p><strong>English:</strong> If the input set is {@code null} or empty, returns {@code null}.</p>
     * <p><strong>Italiano:</strong> Se il set di input è {@code null} o vuoto, restituisce {@code null}.</p>
     *
     * @param entities il set di entità generiche (qualsiasi classe con metodo {@code getId()})
     *                 <p><strong>English:</strong> the set of generic entities (any class having {@code getId()}).</p>
     *                 <p><strong>Italiano:</strong> il set di entità generiche (qualsiasi classe con metodo {@code getId()}).</p>
     * @return un {@code Set<Long>} contenente gli ID delle entità mappate,
     *         o {@code null} se il set di entità è {@code null} o vuoto
     *         <p><strong>English:</strong> a {@code Set<Long>} containing the entity IDs, or {@code null} if input is {@code null} or empty.</p>
     *         <p><strong>Italiano:</strong> un {@code Set<Long>} contenente gli ID delle entità, o {@code null} se l’input è {@code null} o vuoto.</p>
     * @throws RuntimeException se il metodo {@code getId()} non restituisce {@code Long}
     *                        o se si verifica un errore di reflection
     *                        <p><strong>English:</strong> if {@code getId()} does not return {@code Long} or if a reflection error occurs.</p>
     *                        <p><strong>Italiano:</strong> se {@code getId()} non restituisce {@code Long} o si verifica un errore di reflection.</p>
     */
    @Named("toIdSet")
    default Set<Long> toIdSet(Set<?> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .map(entity -> {
                    try {
                        Method getIdMethod = entity.getClass().getMethod("getId");
                        Object id = getIdMethod.invoke(entity);
                        if (id instanceof Long) {
                            return (Long) id;
                        } else {
                            throw new RuntimeException("EntityMapper.getId() method did not return Long for entity: " +
                                    entity.getClass().getSimpleName());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Could not fetch id from entity: " +
                                entity.getClass().getSimpleName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    /**
     * Converte un {@code Set<Role>} in un {@code Set<String>} di nomi di ruolo.
     *
     * <p><strong>English:</strong> Helper to convert a {@code Set<Role>} into a {@code Set<String>} containing role names.</p>
     * <p><strong>Italiano:</strong> Metodo helper per convertire un {@code Set<Role>} in un {@code Set<String>} con i nomi dei ruoli.</p>
     *
     * <p>Se {@code roles} è {@code null} o vuoto, restituisce {@code null}.</p>
     * <p><strong>English:</strong> If {@code roles} is {@code null} or empty, returns {@code null}.</p>
     * <p><strong>Italiano:</strong> Se {@code roles} è {@code null} o vuoto, restituisce {@code null}.</p>
     *
     * @param roles il set di oggetti {@code Role}
     *              <p><strong>English:</strong> the set of {@code Role} objects.</p>
     *              <p><strong>Italiano:</strong> il set di oggetti {@code Role}.</p>
     * @return un {@code Set<String>} contenente i nomi dei ruoli,
     *         o {@code null} se {@code roles} è {@code null} o vuoto
     *         <p><strong>English:</strong> a {@code Set<String>} of role names, or {@code null} if {@code roles} is {@code null} or empty.</p>
     *         <p><strong>Italiano:</strong> un {@code Set<String>} dei nomi dei ruoli, o {@code null} se {@code roles} è {@code null} o vuoto.</p>
     */
    @Named("rolesToNameSet")
    default Set<String> rolesToNameSet(Set<Role> roles){
        if(roles == null || roles.isEmpty()){
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }




    // Persona
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "teamIds", source = "teams", qualifiedByName = "toIdSet")
    @Mapping(target = "projectIds", source = "projects", qualifiedByName = "toIdSet")
    PersonaDTO toDto(Persona persona);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "projects", ignore = true)
    Persona toEntity(PersonaDTO dto);

    //Audit Log
    @Mapping(target = "changedByAccountId", source = "changedBy.id")
    AuditLogDTO toDto(AuditLog auditLog);

    @Mapping(target = "changedBy", ignore = true)
    AuditLog toEntity(AuditLogDTO dto);

    // Role
    @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "toIdSet")
    RoleDTO toDto(Role role);

    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleDTO dto);

    // Permission
    PermissionDTO toDto(Permission permission);

    Permission toEntity(PermissionDTO dto);

    // Department
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "personaIds", source = "personas", qualifiedByName = "toIdSet")
    DepartmentDTO toDto(Department dept);

    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "personas", ignore = true)
    Department toEntity(DepartmentDTO dto);

    // Position
    @Mapping(target = "personaIds", source = "personas", qualifiedByName = "toIdSet")
    PositionDTO toDto(Position pos);

    @Mapping(target = "personas", ignore = true)
    Position toEntity(PositionDTO dto);

    // Team
    @Mapping(target = "memberIds", source = "members", qualifiedByName = "toIdSet")
    TeamDTO toDto(Team team);

    @Mapping(target = "members", ignore = true)
    Team toEntity(TeamDTO dto);

    // Project
    @Mapping(target = "teamMemberIds", source = "team", qualifiedByName = "toIdSet")
    @Mapping(target = "taskIds", source = "tasks", qualifiedByName = "toIdSet")
    ProjectDTO toDto(Project project);

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectDTO dto);

    // Task
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "projectId", source = "project.id")
    TaskDTO toDto(Task task);

    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task toEntity(TaskDTO dto);



}