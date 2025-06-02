package dev.eltoncosta.notesyncapi.controllers.response;

import lombok.Builder;

@Builder
public record UsuarioResumoResponse(
        Long idUsuario,
        String nome
) {
}
