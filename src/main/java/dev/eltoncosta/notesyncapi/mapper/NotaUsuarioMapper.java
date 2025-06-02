package dev.eltoncosta.notesyncapi.mapper;

import dev.eltoncosta.notesyncapi.controllers.response.NotaResumoResponse;
import dev.eltoncosta.notesyncapi.controllers.response.UsuarioResumoResponse;
import dev.eltoncosta.notesyncapi.entities.Nota;
import org.springframework.stereotype.Component;

@Component
public class NotaUsuarioMapper {

    public NotaResumoResponse toNotaResumoResponse(Nota nota) {
        return NotaResumoResponse.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .build();
    }

    public UsuarioResumoResponse toUsuarioResumoResponse(Long id, String nome) {
        return UsuarioResumoResponse.builder()
                .idUsuario(id)
                .nome(nome)
                .build();
    }
}