package dev.eltoncosta.notesyncapi.Mappers;

import dev.eltoncosta.notesyncapi.DTOs.UserDTO;
import dev.eltoncosta.notesyncapi.Models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User map(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setSenha(userDTO.getSenha());
        return user;
    }

    public UserDTO map(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNome(user.getNome());
        userDTO.setEmail(user.getEmail());
        userDTO.setSenha(user.getSenha());
        return userDTO;
    }
}
