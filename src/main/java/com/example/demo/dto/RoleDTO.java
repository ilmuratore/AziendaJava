package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDTO{

    private Long id;
    private String name;
    private String description;
    private Set<Long> permissionIds;

}