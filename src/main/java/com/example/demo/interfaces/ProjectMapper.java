package com.example.demo.interfaces;

import com.example.demo.dto.*;
import com.example.demo.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

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

    // Account
    @Mapping(target = "personaId", source = "persona.id")
    @Mapping(target = "roleIds", source = "roles", qualifiedByName = "toIdSet")
    AccountDTO toDto(Account account);

    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Account toEntity(AccountDTO dto);

    //Audit Log
    @Mapping(target = "changedByAccountId", source = "changedBy.id")
    AuditLogDTO toDto(AuditLog auditLog);

    @Mapping(target = "changedBy", ignore = true)
    AuditLog toEntity(AuditLogDTO dto);

    // Role
    @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "toIdSet")
    RoleDTO toDto(Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Role toEntity(RoleDTO dto);

    // Permission
    PermissionDTO toDto(Permission permission);

    @Mapping(target = "roles", ignore = true)
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
    @Mapping(target = "projectIds", source = "projects", qualifiedByName = "toIdSet")
    TeamDTO toDto(Team team);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "projects", ignore = true)
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

    /**
     * Helper method per la conversione Set<Entity> a Set<Long> per gli id
     * Utilizza reflection per ottenere l'id da qualsiasi entità che abbia un metodo getId()
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
                            throw new RuntimeException("getId() method did not return Long for entity: " +
                                    entity.getClass().getSimpleName());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Could not fetch id from entity: " +
                                entity.getClass().getSimpleName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }
}