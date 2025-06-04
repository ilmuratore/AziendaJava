package com.example.demo.config.exceptions;


/**
 * Eccezione lanciata quando il login fallisce per credenziali errate o altri motivi di autenticazione.
 *
 * <p><strong>English:</strong> Exception thrown when a login attempt fails due to invalid credentials
 * or other authentication issues.</p>
 * <p><strong>Italiano:</strong> Eccezione lanciata quando un tentativo di login fallisce a causa di
 * credenziali non valide o altri problemi di autenticazione.</p>
 */
public class FailedLoginException extends RuntimeException{

    /**
     * Costruisce una nuova istanza di {@code FailedLoginException} con un messaggio dettagliato.
     *
     * <p><strong>English:</strong> Constructs a new {@code FailedLoginException} with the specified detail message.</p>
     * <p><strong>Italiano:</strong> Costruisce una nuova {@code FailedLoginException} con il messaggio di dettaglio specificato.</p>
     *
     * @param message il messaggio di dettaglio che descrive il motivo del fallimento del login
     *                <p><strong>English:</strong> the detail message explaining why the login failed.</p>
     *                <p><strong>Italiano:</strong> il messaggio di dettaglio che descrive il motivo del fallimento del login.</p>
     */
    public FailedLoginException(String message){
        super(message);
    }
}
