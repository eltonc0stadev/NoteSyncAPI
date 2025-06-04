package dev.eltoncosta.notesyncapi.mapper;

import dev.eltoncosta.notesyncapi.controllers.request.NotaRequest;
import dev.eltoncosta.notesyncapi.controllers.request.NotaUpdateRequest;
import dev.eltoncosta.notesyncapi.controllers.response.NotaResponse;
import dev.eltoncosta.notesyncapi.controllers.response.NotaResumoResponse;
import dev.eltoncosta.notesyncapi.entities.Nota;
import dev.eltoncosta.notesyncapi.entities.Usuario;
import dev.eltoncosta.notesyncapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotaMapper {

    private final UsuarioRepository usuarioRepository;
    private final NotaUsuarioMapper notaUsuarioMapper;

    public Nota toNota(NotaUpdateRequest notaRequest, Usuario dono) {
        List<Usuario> usuariosCompartilhados = new ArrayList<>();
        if (notaRequest.usuariosCompartilhadosIds() != null && !notaRequest.usuariosCompartilhadosIds().isEmpty()) {
            usuariosCompartilhados = notaRequest.usuariosCompartilhadosIds().stream()
                    .map(id -> usuarioRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Usuário compartilhado não encontrado: " + id)))
                    .toList();
        }
        return Nota.builder()
                .id(notaRequest.id())
                .titulo(notaRequest.titulo())
                .conteudo(notaRequest.conteudo())
                .arquivada(notaRequest.arquivada() != null ? notaRequest.arquivada() : false)
                .lixeira(notaRequest.lixeira() != null ? notaRequest.lixeira() : false)
                .dono(dono)
                .usuariosCompartilhados(usuariosCompartilhados)
                .build();
    }

    public Nota toNota(NotaRequest notaRequest, Usuario dono) {
        List<Usuario> usuariosCompartilhados = new ArrayList<>();
        if (notaRequest.usuariosCompartilhadosIds() != null && !notaRequest.usuariosCompartilhadosIds().isEmpty()) {
            usuariosCompartilhados = usuarioRepository.findAllById(notaRequest.usuariosCompartilhadosIds());
        }
        return Nota.builder()
                .titulo(notaRequest.titulo())
                .conteudo(notaRequest.conteudo())
                .arquivada(notaRequest.arquivada() != null ? notaRequest.arquivada() : false)
                .lixeira(notaRequest.lixeira() != null ? notaRequest.lixeira() : false)
                .dono(dono)
                .usuariosCompartilhados(usuariosCompartilhados)
                .build();
    }

    public NotaResponse toNotaResponse(Nota nota) {

        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


        return NotaResponse.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .conteudo(nota.getConteudo())
                .arquivada(nota.getArquivada())
                .lixeira(nota.getLixeira())
                .dataCriacao(nota.getDataCriacao().format(FORMATTER))
                .dataAtualizacao(nota.getDataAtualizacao() != null ? nota.getDataAtualizacao().format(FORMATTER) : null)
                .donoId(notaUsuarioMapper.toUsuarioResumoResponse(nota.getDono().getId(), nota.getDono().getNome()))
                .usuariosCompartilhados(nota.getUsuariosCompartilhados().stream()
                        .map(usuario -> notaUsuarioMapper.toUsuarioResumoResponse(usuario.getId(), usuario.getNome()))
                        .toList())
                .build();
    }

    public NotaResumoResponse toNotaResumoResponse(Nota nota) {
        return notaUsuarioMapper.toNotaResumoResponse(nota);
    }
}

