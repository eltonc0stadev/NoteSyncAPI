package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.exceptions.NotaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationControllerAdvice {
    // This class can be used to handle exceptions globally across all controllers
    // You can define methods annotated with @ExceptionHandler to handle specific exceptions
    // and return custom responses or error messages.

    // Example:
    // @ExceptionHandler(SomeException.class)
    // public ResponseEntity<ErrorResponse> handleSomeException(SomeException ex) {
    //     ErrorResponse errorResponse = new ErrorResponse("Error message", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    // }

    @ExceptionHandler(NotaNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotFoundOrInvalidException(NotaNotFoundException ex) {
        return ex.getMessage();
    }

}
