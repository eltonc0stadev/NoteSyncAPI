package dev.eltoncosta.notesyncapi.Controllers;

import dev.eltoncosta.notesyncapi.DTOs.UserDTO;
import dev.eltoncosta.notesyncapi.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService ninjaService;

    public UserController(UserService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @PostMapping("/criar")
    public UserDTO criarUser(@RequestBody UserDTO user) {
        return ninjaService.criarUsuario(user);
    }

    @GetMapping("/listar")
    public List<UserDTO> listarUsuarios() {
        return ninjaService.listarUsuarios();
    }

    @GetMapping("/buscar/{id}")
    public UserDTO buscarUsuario(@PathVariable Long id) {
        return ninjaService.buscarUsuario(id);
    }

    @PutMapping("/atualizar/{id}")
    public UserDTO atualizarUsuario(@PathVariable Long id, @RequestBody UserDTO user) {
        UserDTO userAtualizado = ninjaService.atualizarUsuario(id, user);
        if (userAtualizado != null) {
            return userAtualizado;
        }
        return null;
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        ninjaService.deletarUsuario(id);
    }

}
