package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.controllers.request.*;
import dev.eltoncosta.notesyncapi.controllers.response.NotaResponse;
import dev.eltoncosta.notesyncapi.entities.Nota;
import dev.eltoncosta.notesyncapi.mapper.NotaMapper;
import dev.eltoncosta.notesyncapi.services.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notesync/nota")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;
    private final NotaMapper notaMapper;

    @GetMapping("/listar")
    public ResponseEntity<List<NotaResponse>> listarNotas(@RequestBody NotaResumoRequest notaRequest) {
        List<Nota> notaList = notaService.listarNotas(notaRequest);
        if (!notaList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notaList.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/criar")
    public ResponseEntity<NotaResponse> criarNota(@RequestBody NotaRequest nota) {
        Nota notaCriada = notaService.criarNota(notaMapper.toNota(nota));
        return ResponseEntity.status(HttpStatus.CREATED).body(notaMapper.toNotaResponse(notaCriada));
    }

    @PutMapping("/deletar")
    public ResponseEntity<NotaResponse> deletarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaDeletada = notaService.deletarNota(notaMapper.toNota(nota));
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaDeletada));
    }

    @PutMapping("/restaurar")
    public ResponseEntity<NotaResponse> restaurarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaRestaurada = notaService.restaurarNota(notaMapper.toNota(nota));
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaRestaurada));
    }

    @PutMapping("/arquivar")
    public ResponseEntity<NotaResponse> arquivarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaArquivada = notaService.arquivarNota(notaMapper.toNota(nota));
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaArquivada));
    }

    @PutMapping("/desarquivar")
    public ResponseEntity<NotaResponse> desarquivarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaDesarquivada = notaService.desarquivarNota(notaMapper.toNota(nota));
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaDesarquivada));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<NotaResponse> atualizarNota(@RequestBody NotaUpdateRequest nota) {
        Nota notaAtualizada = notaService.atualizarNota(notaMapper.toNota(nota));
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaAtualizada));
    }

    @PutMapping("/adicionar-amigo")
    public ResponseEntity<NotaResponse> adicionarUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Nota nota = notaService.buscarNotaPorId(notaAddUserCompRequest.idNota());
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaService.adicionarUsuarioCompartilhado(nota, notaAddUserCompRequest.idUsersComp())));
    }

    @PutMapping("/remover-amigo")
    public ResponseEntity<NotaResponse> removerUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Nota nota = notaService.buscarNotaPorId(notaAddUserCompRequest.idNota());
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaService.removerUsuariosCompartilhados(nota, notaAddUserCompRequest.idUsersComp())));
    }

    @GetMapping("/arquivadas")
    public ResponseEntity<List<NotaResponse>> listarNotasArquivadas(@RequestBody UsuarioResumoRequest usuarioRequest) {
        List<Nota> notasArquivadas = notaService.listarNotasArquivadas(usuarioRequest);
        if (!notasArquivadas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notasArquivadas.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/lixeira")
    public ResponseEntity<List<NotaResponse>> listarNotasLixeira(@RequestBody UsuarioResumoRequest usuarioRequest) {
        List<Nota> notasLixeira = notaService.listarNotasLixeira(usuarioRequest);
        if (!notasLixeira.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notasLixeira.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
