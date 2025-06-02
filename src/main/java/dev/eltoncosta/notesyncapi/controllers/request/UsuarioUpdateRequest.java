package dev.eltoncosta.notesyncapi.controllers.request;

public record UsuarioUpdateRequest(Long id,
                                   String nome,
                                   String email,
                                   String senha,
                                   String idEstudante) {
}
