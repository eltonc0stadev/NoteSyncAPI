package dev.eltoncosta.notesyncapi.controllers;

import dev.eltoncosta.notesyncapi.controllers.request.UsuarioRequest;
import dev.eltoncosta.notesyncapi.controllers.request.UsuarioUpdateRequest;
import dev.eltoncosta.notesyncapi.controllers.response.UsuarioResponse;
import dev.eltoncosta.notesyncapi.controllers.response.UsuarioResumoResponse;
import dev.eltoncosta.notesyncapi.entities.Usuario;
import dev.eltoncosta.notesyncapi.mapper.UsuarioMapper;
import dev.eltoncosta.notesyncapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/notesync/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        // Atualiza apenas os campos permitidos do próprio usuário
        usuario.setNome(usuarioUpdateRequest.nome() != null ? usuarioUpdateRequest.nome() : usuario.getNome());
        usuario.setIdEstudante(usuarioUpdateRequest.idEstudante() != null ? usuarioUpdateRequest.idEstudante() : usuario.getIdEstudante());
        if (usuarioUpdateRequest.senha() != null && !usuarioUpdateRequest.senha().isBlank()) {
            usuario.setSenha(usuarioUpdateRequest.senha());
        }
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResponse(usuarioAtualizado));
    }

    @PutMapping("/desativar")
    public ResponseEntity<UsuarioResumoResponse> desativarUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        Usuario usuarioDesativado = usuarioService.desativarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResumoResponse(usuarioDesativado));
    }

    @PutMapping("/ativar")
    public ResponseEntity<UsuarioResumoResponse> ativarUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        Usuario usuarioAtivado = usuarioService.ativarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResumoResponse(usuarioAtivado));
    }

    @PutMapping("/atualizar-senha")
    public ResponseEntity<UsuarioResponse> atualizarSenha(@RequestBody String novaSenha) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        Usuario usuarioAtualizado = usuarioService.atualizarSenha(usuario.getId(), novaSenha);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResponse(usuarioAtualizado));
    }

}
