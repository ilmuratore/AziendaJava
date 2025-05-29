package com.example.demo.interfaces;

import com.example.demo.dto.PermessoDTO;

import java.util.List;


public interface PermessoService {
    PermessoDTO crea(PermessoDTO dto);
    PermessoDTO aggiorna(Integer id, PermessoDTO dto);
    void elimina(Integer id);
    PermessoDTO trovaPerId(Integer id);
    List<PermessoDTO> trovaTutti();
}
