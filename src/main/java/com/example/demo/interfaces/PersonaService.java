package com.example.demo.interfaces;

import com.example.demo.dto.PersonaDTO;

import java.util.List;

public interface PersonaService {
    PersonaDTO crea(PersonaDTO dto);
    PersonaDTO aggiorna(Integer id, PersonaDTO dto);
    void elimina(Integer id);
    PersonaDTO trovaPerId(Integer id);
    List<PersonaDTO> trovaTutti();

    PersonaDTO trovaPerCodiceFiscale(String codiceFiscale);
    void eliminaPerCodiceFiscale(String codiceFiscale);
    PersonaDTO aggiornaPerCodiceFiscale(String codiceFiscale, PersonaDTO dto);


}


