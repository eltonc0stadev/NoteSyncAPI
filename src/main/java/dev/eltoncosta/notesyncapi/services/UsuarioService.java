package dev.eltoncosta.notesyncapi.services;

import dev.eltoncosta.notesyncapi.entities.Usuario;
import dev.eltoncosta.notesyncapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getNome() == null || usuario.getEmail() == null || usuario.getSenha() == null || usuario.getIdEstudante() == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }
        usuario.setDesativado(false);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());
        if (optionalUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        Usuario usuarioExistente = optionalUsuario.get();
        usuarioExistente.setId(usuario.getId());
        usuarioExistente.setNome(usuario.getNome() != null ? usuario.getNome() : usuarioExistente.getNome());
        usuarioExistente.setEmail(usuario.getEmail() != null ? usuario.getEmail() : usuarioExistente.getEmail());
        usuarioExistente.setSenha(usuario.getSenha() != null ? usuario.getSenha() : usuarioExistente.getSenha());
        usuarioExistente.setIdEstudante(usuario.getIdEstudante() != null ? usuario.getIdEstudante() : usuarioExistente.getIdEstudante());
        usuarioExistente.setDesativado(usuario.getDesativado() != null ? usuario.getDesativado() : usuarioExistente.getDesativado());
        usuarioExistente.setNotasProprias(usuario.getNotasProprias() != null ? usuario.getNotasProprias() : usuarioExistente.getNotasProprias());
        usuarioExistente.setNotasCompartilhadas(usuario.getNotasCompartilhadas() != null ? usuario.getNotasCompartilhadas() : usuarioExistente.getNotasCompartilhadas());
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario desativarUsuario(Usuario usuario) {
        usuario.setDesativado(true);
        return this.atualizarUsuario(usuario);
    }

    public Usuario ativarUsuario(Usuario usuario) {
        usuario.setDesativado(false);
        return this.atualizarUsuario(usuario);
    }

    private List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return List.of();
        }
        return usuarios;
    }

    private Usuario obterUsuarioPorID(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

}
