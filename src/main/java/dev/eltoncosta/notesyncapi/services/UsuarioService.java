package dev.eltoncosta.notesyncapi.services;

import dev.eltoncosta.notesyncapi.entities.Usuario;
import dev.eltoncosta.notesyncapi.exceptions.UsuarioNotFoundException;
import dev.eltoncosta.notesyncapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getNome() == null || usuario.getEmail() == null || usuario.getSenha() == null || usuario.getIdEstudante() == null) {
            throw new UsuarioNotFoundException("Usuário inválido ou não encontrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setDesativado(false);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new UsuarioNotFoundException("Usuário inválido ou não encontrado");
        }
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());
        if (optionalUsuario.isEmpty()) {
            throw new UsuarioNotFoundException("Usuário não encontrado ou inexistente");
        }
        Usuario usuarioExistente = optionalUsuario.get();
        usuarioExistente.setId(usuario.getId());
        usuarioExistente.setNome(usuario.getNome() != null ? usuario.getNome() : usuarioExistente.getNome());
        usuarioExistente.setEmail(usuario.getEmail() != null ? usuario.getEmail() : usuarioExistente.getEmail());
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        usuarioExistente.setIdEstudante(usuario.getIdEstudante() != null ? usuario.getIdEstudante() : usuarioExistente.getIdEstudante());
        usuarioExistente.setDesativado(usuario.getDesativado() != null ? usuario.getDesativado() : usuarioExistente.getDesativado());
        usuarioExistente.setNotasProprias(usuario.getNotasProprias() != null ? usuario.getNotasProprias() : usuarioExistente.getNotasProprias());
        usuarioExistente.setNotasCompartilhadas(usuario.getNotasCompartilhadas() != null ? usuario.getNotasCompartilhadas() : usuarioExistente.getNotasCompartilhadas());
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario atualizarSenha(Long id, String novaSenha) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new UsuarioNotFoundException("Usuário não encontrado ou inexistente");
        }
        Usuario usuario = optionalUsuario.get();
        usuario.setSenha(novaSenha);
        return atualizarUsuario(usuario);
    }

    public Usuario desativarUsuario(Usuario usuario) {
        usuario.setDesativado(true);
        return this.atualizarUsuario(usuario);
    }

    public Usuario ativarUsuario(Usuario usuario) {
        usuario.setDesativado(false);
        return this.atualizarUsuario(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o e-mail informado"));
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
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado ou inexistente"));
    }

}
