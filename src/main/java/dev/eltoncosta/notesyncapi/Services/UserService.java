package dev.eltoncosta.notesyncapi.Services;

import dev.eltoncosta.notesyncapi.Models.User;
import dev.eltoncosta.notesyncapi.Repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Criar usuario
    public User criarUsuario(User user) {
        if (user.getNome() == null || user.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        return userRepository.save(user);
    }

    // Listar usuario
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    // Buscar usuario por id
    public User buscarUsuario(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Deletar usuario
    public void deletarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    // Atualizar usuario
    public User atualizarUsuario(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setNome(user.getNome());
            existingUser.setEmail(user.getEmail());
            existingUser.setSenha(user.getSenha());
            return userRepository.save(existingUser);
        }
        return null;
    }

}
