package dev.eltoncosta.notesyncapi.exceptions;

public class NotaNotFoundException extends RuntimeException{
    public NotaNotFoundException(String message) {
        super(message);
    }
}
