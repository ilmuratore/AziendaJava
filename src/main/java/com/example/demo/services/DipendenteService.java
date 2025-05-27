package com.example.demo.services;

import com.example.demo.dto.DipendenteDTO;

import java.util.List;

public interface DipendenteService {
    DipendenteDTO crea(DipendenteDTO dto);
    DipendenteDTO aggiorna(Integer id, DipendenteDTO dto);
    void elimina(Integer id);
    DipendenteDTO trovaPerId(Integer id);
    List<DipendenteDTO> trovaTutti();

    DipendenteDTO trovaPerCodiceFiscale(String codiceFiscale);
    void eliminaPerCodiceFiscale(String codiceFiscale);
    DipendenteDTO aggiornaPerCodiceFiscale(String codiceFiscale, DipendenteDTO dto);


}


