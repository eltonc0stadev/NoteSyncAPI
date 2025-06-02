package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.controllers.request.*;
import dev.eltoncosta.notesyncapi.controllers.response.NotaResponse;
import dev.eltoncosta.notesyncapi.entities.Nota;
import dev.eltoncosta.notesyncapi.mapper.NotaMapper;
import dev.eltoncosta.notesyncapi.services.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notesync/nota")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;
    private final NotaMapper notaMapper;

    @GetMapping("/listar")
    public List<NotaResponse> listarNotas(@RequestBody NotaUpdateRequest notaRequest) {
        List<Nota> notaList = notaService.listarNotas(notaRequest);
        if (!notaList.isEmpty()) {
            return notaList.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList();
        }
        return List.of();
    }

    @PostMapping("/criar")
    public NotaResponse criarNota(@RequestBody NotaRequest nota) {
        Nota notaCriada = notaService.criarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaCriada);
    }

    @PutMapping("/deletar")
    public NotaResponse deletarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaDeletada = notaService.deletarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaDeletada);
    }

    @PutMapping("/restaurar")
    public NotaResponse restaurarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaRestaurada = notaService.restaurarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaRestaurada);
    }

    @PutMapping("/arquivar")
    public NotaResponse arquivarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaArquivada = notaService.arquivarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaArquivada);
    }

    @PutMapping("/desarquivar")
    public NotaResponse desarquivarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaDesarquivada = notaService.desarquivarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaDesarquivada);
    }

    @PutMapping("/atualizar")
    public NotaResponse atualizarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaAtualizada = notaService.atualizarNota(notaMapper.toNota(nota));
        return notaMapper.toNotaResponse(notaAtualizada);
    }

    @PutMapping("/adicionar-amigo")
    public NotaResponse adicionarUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Nota nota = notaService.buscarNotaPorId(notaAddUserCompRequest.idNota());
        return notaMapper.toNotaResponse(notaService.adicionarUsuarioCompartilhado(nota, notaAddUserCompRequest.idUsersComp()));
    }

    @PutMapping("/remover-amigo")
    public NotaResponse removerUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Nota nota = notaService.buscarNotaPorId(notaAddUserCompRequest.idNota());
        return notaMapper.toNotaResponse(notaService.removerUsuariosCompartilhados(nota, notaAddUserCompRequest.idUsersComp()));
    }

    @GetMapping("/arquivadas")
    public List<NotaResponse> listarNotasArquivadas(@RequestBody UsuarioResumoRequest usuarioRequest) {
        List<Nota> notasArquivadas = notaService.listarNotasArquivadas(usuarioRequest);
        if (!notasArquivadas.isEmpty()) {
            return notasArquivadas.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList();
        }
        return List.of();
    }

    @GetMapping("/lixeira")
    public List<NotaResponse> listarNotasLixeira(@RequestBody UsuarioResumoRequest usuarioRequest) {
        List<Nota> notasLixeira = notaService.listarNotasLixeira(usuarioRequest);
        if (!notasLixeira.isEmpty()) {
            return notasLixeira.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList();
        }
        return List.of();
    }

}
