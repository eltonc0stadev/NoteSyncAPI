package dev.eltoncosta.notesyncapi.controllers.response;

public record UserDetailsResponse(
    Long id,
    String nome,
    String email,
    String idEstudante
) {}
