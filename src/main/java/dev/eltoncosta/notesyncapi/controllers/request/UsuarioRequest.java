package dev.eltoncosta.notesyncapi.controllers.request;

public record UsuarioRequest(String nome,
                             String email,
                             String senha,
                             String idEstudante) {
}
