package com.example.demo.config.exceptions;

/**
 * Eccezione lanciata quando un’entità richiesta non è presente nel database o risorsa associata.
 *
 * <p><strong>English:</strong> Exception thrown when a requested entity cannot be found in the database
 * or associated resource is missing.</p>
 * <p><strong>Italiano:</strong> Eccezione lanciata quando un’entità richiesta non viene trovata nel database
 * o la risorsa associata è assente.</p>
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Costruisce una nuova istanza di {@code EntityNotFoundException} con un messaggio descrittivo.
     *
     * <p><strong>English:</strong> Constructs a new {@code EntityNotFoundException} with the specified detail message.</p>
     * <p><strong>Italiano:</strong> Costruisce una nuova {@code EntityNotFoundException} con il messaggio di dettaglio specificato.</p>
     *
     * @param message il messaggio di dettaglio che descrive quale entità non è stata trovata
     *                <p><strong>English:</strong> the detail message explaining which entity was not found.</p>
     *                <p><strong>Italiano:</strong> il messaggio di dettaglio che descrive quale entità non è stata trovata.</p>
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
