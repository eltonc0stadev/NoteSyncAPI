package dev.eltoncosta.notesyncapi.mapper;

import dev.eltoncosta.notesyncapi.controllers.request.UsuarioRequest;
import dev.eltoncosta.notesyncapi.controllers.request.UsuarioUpdateRequest;
import dev.eltoncosta.notesyncapi.controllers.response.UsuarioResponse;
import dev.eltoncosta.notesyncapi.controllers.response.UsuarioResumoResponse;
import dev.eltoncosta.notesyncapi.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final NotaUsuarioMapper notaUsuarioMapper;

    public Usuario toUsuario(UsuarioRequest usuarioRequest) {
        return Usuario.builder()
                .nome(usuarioRequest.nome())
                .email(usuarioRequest.email())
                .senha(usuarioRequest.senha())
                .idEstudante(usuarioRequest.idEstudante())
                .build();
    }

    public Usuario toUsuario(UsuarioUpdateRequest usuarioRequest) {
        return Usuario.builder()
                .id(usuarioRequest.id())
                .nome(usuarioRequest.nome())
                .email(usuarioRequest.email())
                .senha(usuarioRequest.senha())
                .idEstudante(usuarioRequest.idEstudante())
                .build();
    }

    public UsuarioResumoResponse toUsuarioResumoResponse(Usuario usuario) {
        Long id = usuario.getId();
        String nome = usuario.getNome();
        return notaUsuarioMapper.toUsuarioResumoResponse(id, nome);
    }

    public UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .idEstudante(usuario.getIdEstudante())
                .dataCriacao(String.valueOf(usuario.getDataCriacao()))
                .dataAtualizacao(String.valueOf(usuario.getDataAtualizacao()))
                .desativado(usuario.getDesativado())
                .notasProprias(
                        usuario.getNotasProprias().stream()
                                .map(notaUsuarioMapper::toNotaResumoResponse)
                                .toList()
                )
                .notasCompartilhadas(
                        usuario.getNotasCompartilhadas().stream()
                                .map(notaUsuarioMapper::toNotaResumoResponse)
                                .toList()
                )
                .build();
    }
}