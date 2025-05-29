package com.example.demo.interfaces;

import com.example.demo.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO crea(UserDTO dto);
    UserDTO aggiorna(Integer id, UserDTO dto);
    void elimina(Integer id);
    UserDTO trovaPerId(Integer id);
    List<UserDTO> trovaTutti();
}
