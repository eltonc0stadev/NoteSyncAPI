package dev.eltoncosta.notesyncapi.Services;

import dev.eltoncosta.notesyncapi.DTOs.UserDTO;
import dev.eltoncosta.notesyncapi.Mappers.UserMapper;
import dev.eltoncosta.notesyncapi.Models.User;
import dev.eltoncosta.notesyncapi.Repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Criar usuario
    public UserDTO criarUsuario(UserDTO user) {
        User userModel = userMapper.map(user);
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
        userModel = userRepository.save(userModel);
        return userMapper.map(userModel);
    }

    // Listar usuario
    public List<UserDTO> listarUsuarios() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::map).collect(Collectors.toList());
    }

    // Buscar usuario por id
    public UserDTO buscarUsuario(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::map).orElse(null);
    }

    // Deletar usuario
    public void deletarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    // Atualizar usuario
    public UserDTO atualizarUsuario(Long id, UserDTO user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userExistente = optionalUser.get();
            userExistente.setNome(user.getNome());
            userExistente.setEmail(user.getEmail());
            userExistente.setSenha(user.getSenha());
            return userMapper.map(userRepository.save(userExistente));
        }
        return null;
    }

}
