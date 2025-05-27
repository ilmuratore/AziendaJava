package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private Integer permessoId;

}