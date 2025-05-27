package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PermessoDTO {
    private Integer id;
    private String tipoPermesso;
    private List<Integer> accountIds;

}

