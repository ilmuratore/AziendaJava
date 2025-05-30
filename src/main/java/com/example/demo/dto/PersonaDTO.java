package com.example.demo.dto;

import com.example.demo.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PersonaDTO{

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String taxCode;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String email;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private Instant createdAt;
    private Instant updatedAt;
    private Long accountId;
    private Long departmentId;
    private Long positionId;
    private Set<Long> teamIds;
    private Set<Long> projectIds;
}