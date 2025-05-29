package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username" , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(name = "email" , nullable = false)
    private String email;

}
