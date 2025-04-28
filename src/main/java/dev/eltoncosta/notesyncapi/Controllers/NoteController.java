package dev.eltoncosta.notesyncapi.Controllers;

import dev.eltoncosta.notesyncapi.DTOs.NoteDTO;
import dev.eltoncosta.notesyncapi.Models.Note;
import dev.eltoncosta.notesyncapi.Models.User;
import dev.eltoncosta.notesyncapi.Services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Adicionar nota
    @PostMapping("/criar")
    public ResponseEntity<String> criarNota(@RequestBody NoteDTO note, @RequestParam Long userId) {
        User user = new User();
        user.setId(userId);
        note.setUser(user);

        NoteDTO notaCriada = noteService.criarNota(note);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Nota criada com sucesso: " + notaCriada.getTitulo() + " " + notaCriada.getId());
    }

    // Procurar nota por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<NoteDTO> buscarNota(@PathVariable Long id) {
        NoteDTO note = noteService.buscarNota(id);
        if (note != null) {
            return ResponseEntity.ok(note);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Mostrar todas as notas de um usuario
    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<NoteDTO>> listarNotasDoUsuario(@PathVariable Long idUsuario) {
        List<NoteDTO> listaDeNotas = noteService.listarNotasDoUsuario(idUsuario);
        return ResponseEntity.ok(listaDeNotas);
    }

    // Alterar dados das notas
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarNota(@PathVariable Long id, @RequestBody NoteDTO noteAtualizado) {
        NoteDTO note = noteService.atualizarNota(id, noteAtualizado);
        if (note != null) {
            return ResponseEntity.ok("Nota atualizada com sucesso: " + note.getTitulo() + " " + note.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nota de ID, " + id + " não encontrada");
    }

    // Deletar notas
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarNota(@PathVariable Long id) {
        NoteDTO note = noteService.buscarNota(id);
        if (note != null) {
            noteService.deletarNota(id);
            return ResponseEntity.ok("Nota deletada com sucesso: " + note.getId() + " " + note.getTitulo());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nota de ID, " + id + " não encontrada");
    }

}
