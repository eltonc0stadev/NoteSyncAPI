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

@RestController
@RequestMapping("/api/notesync/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResumoResponse> criarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioRequest);
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toUsuarioResumoResponse(usuarioCriado));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioUpdateRequest);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResponse(usuarioAtualizado));
    }

    @PutMapping("/desativar")
    public ResponseEntity<UsuarioResumoResponse> desativarUsuario(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioUpdateRequest);
        Usuario usuarioDesativado = usuarioService.desativarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResumoResponse(usuarioDesativado));
    }

    @PutMapping("/ativar")
    public ResponseEntity<UsuarioResumoResponse> ativarUsuario(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioUpdateRequest);
        Usuario usuarioAtivado = usuarioService.ativarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toUsuarioResumoResponse(usuarioAtivado));
    }

}
