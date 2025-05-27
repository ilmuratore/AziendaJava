package com.example.demo.dto;

import com.example.demo.entities.Permesso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private PermessoDTO permesso;

}

