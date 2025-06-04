package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.controllers.request.*;
import dev.eltoncosta.notesyncapi.controllers.response.NotaResponse;
import dev.eltoncosta.notesyncapi.entities.Nota;
import dev.eltoncosta.notesyncapi.mapper.NotaMapper;
import dev.eltoncosta.notesyncapi.services.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notesync/nota")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;
    private final NotaMapper notaMapper;

    @GetMapping("/listar")
    public ResponseEntity<List<NotaResponse>> listarNotas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Nota> notaList = notaService.listarNotasPorEmailUsuario(email);
        if (!notaList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notaList.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/criar")
    public ResponseEntity<NotaResponse> criarNota(@RequestBody NotaRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaCriada = notaService.criarNotaParaUsuario(nota, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaMapper.toNotaResponse(notaCriada));
    }

    @PutMapping("/deletar")
    public ResponseEntity<NotaResponse> deletarNota(@RequestBody NotaUpdateRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaDeletada = notaService.deletarNotaParaUsuario(nota, email);
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaDeletada));
    }

    @PutMapping("/restaurar")
    public ResponseEntity<NotaResponse> restaurarNota(@RequestBody NotaUpdateRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaRestaurada = notaService.restaurarNotaParaUsuario(nota, email);
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaRestaurada));
    }

    @PutMapping("/arquivar")
    public ResponseEntity<NotaResponse> arquivarNota(@RequestBody NotaUpdateRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaArquivada = notaService.arquivarNotaParaUsuario(nota, email);
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaArquivada));
    }

    @PutMapping("/desarquivar")
    public ResponseEntity<NotaResponse> desarquivarNota(@RequestBody NotaUpdateRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaDesarquivada = notaService.desarquivarNotaParaUsuario(nota, email);
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaDesarquivada));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<NotaResponse> atualizarNota(@RequestBody NotaUpdateRequest nota) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaAtualizada = notaService.atualizarNotaParaUsuario(nota, email);
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaAtualizada));
    }

    @PutMapping("/atualizar-titulo")
    public ResponseEntity<NotaResponse> atualizarTitulo(@RequestBody NotaTituloUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaAtualizada = notaService.atualizarTituloParaUsuario(request.id(), request.titulo(), email);
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaAtualizada));
    }

    @PutMapping("/atualizar-conteudo")
    public ResponseEntity<NotaResponse> atualizarConteudo(@RequestBody NotaConteudoUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota notaAtualizada = notaService.atualizarConteudoParaUsuario(request.id(), request.conteudo(), email);
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaAtualizada));
    }

    @PutMapping("/adicionar-amigo")
    public ResponseEntity<NotaResponse> adicionarUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota nota = notaService.buscarNotaPorIdParaUsuario(notaAddUserCompRequest.idNota(), email);
        return ResponseEntity.status(HttpStatus.OK).body(notaMapper.toNotaResponse(notaService.adicionarUsuarioCompartilhado(nota, notaAddUserCompRequest.idUsersComp())));
    }

    @PutMapping("/remover-amigo")
    public ResponseEntity<NotaResponse> removerUsuarioCompartilhado(@RequestBody NotaAddUserComp notaAddUserCompRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Nota nota = notaService.buscarNotaPorIdParaUsuario(notaAddUserCompRequest.idNota(), email);
        return ResponseEntity.ok(notaMapper.toNotaResponse(notaService.removerUsuariosCompartilhados(nota, notaAddUserCompRequest.idUsersComp())));
    }

    @GetMapping("/arquivadas")
    public ResponseEntity<List<NotaResponse>> listarNotasArquivadas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Nota> notasArquivadas = notaService.listarNotasArquivadasPorEmailUsuario(email);
        if (!notasArquivadas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notasArquivadas.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/lixeira")
    public ResponseEntity<List<NotaResponse>> listarNotasLixeira() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Nota> notasLixeira = notaService.listarNotasLixeiraPorEmailUsuario(email);
        if (!notasLixeira.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(notasLixeira.stream()
                    .map(notaMapper::toNotaResponse)
                    .toList());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
