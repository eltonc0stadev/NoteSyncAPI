package dev.eltoncosta.notesyncapi.controllers.response;

import lombok.Builder;

@Builder
public record NotaResumoResponse (
        Long id,
        String titulo
){
}
