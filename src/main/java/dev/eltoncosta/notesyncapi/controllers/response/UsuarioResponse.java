package dev.eltoncosta.notesyncapi.controllers.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String idEstudante,
        Boolean desativado,
        String dataCriacao,
        String dataAtualizacao,
        List<NotaResumoResponse> notasProprias,
        List<NotaResumoResponse> notasCompartilhadas
        ) {
}
