package dev.eltoncosta.notesyncapi.controllers.response;

import lombok.Builder;

import java.util.List;

@Builder
public record NotaResponse(
        Long id,
        String titulo,
        String conteudo,
        Boolean arquivada,
        Boolean lixeira,
        String dataCriacao,
        String dataAtualizacao,
        UsuarioResumoResponse donoId,
        List<UsuarioResumoResponse> usuariosCompartilhados
) {
}
