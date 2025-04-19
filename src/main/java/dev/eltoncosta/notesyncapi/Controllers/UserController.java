package dev.eltoncosta.notesyncapi.Controllers;

import dev.eltoncosta.notesyncapi.Models.User;
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
    public User criarUser(@RequestBody User user) {
        return ninjaService.criarUsuario(user);
    }

    @GetMapping("/listar")
    public List<User> listarUsuarios() {
        return ninjaService.listarUsuarios();
    }

    @DeleteMapping("/buscar/{id}")
    public User buscarUsuario(@PathVariable Long id) {
        return ninjaService.buscarUsuario(id);
    }

    @PutMapping("/atualizar/{id}")
    public User atualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        return ninjaService.atualizarUsuario(id, user);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        ninjaService.deletarUsuario(id);
    }

}
