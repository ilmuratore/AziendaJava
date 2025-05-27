package com.example.demo.services;

import com.example.demo.dto.DipendenteDTOFull;
import com.example.demo.dto.DipendenteDTOLight;

import java.util.List;

public interface DipendenteService {
    DipendenteDTOLight crea(DipendenteDTOLight dto);
    DipendenteDTOLight aggiorna(Integer id, DipendenteDTOLight dto);
    void elimina(Integer id);
    DipendenteDTOLight trovaPerId(Integer id);
    List<DipendenteDTOLight> trovaTutti();

    DipendenteDTOLight trovaPerCodiceFiscale(String codiceFiscale);
    void eliminaPerCodiceFiscale(String codiceFiscale);
    DipendenteDTOLight aggiornaPerCodiceFiscale(String codiceFiscale, DipendenteDTOLight dto);

   // DipendenteDTOFull creaFull(DipendenteDTOFull dto);
   // DipendenteDTOFull aggiornaFull(Integer id, DipendenteDTOFull dto);
   // DipendenteDTOFull aggiornaCfFull(String codiceFiscale, DipendenteDTOFull dto);
   // DipendenteDTOFull trovaPerIdFull(Integer id);
   // DipendenteDTOFull trovaPerCodiceFiscaleFull(String codiceFiscale);
   // List<DipendenteDTOFull> trovaTuttiFull();
}


