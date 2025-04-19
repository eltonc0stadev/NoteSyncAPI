package dev.eltoncosta.notesyncapi.Repositorys;

import dev.eltoncosta.notesyncapi.Models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    // Metodo para encontrar notas por ID do usuário
    List<Note> findByUserId(Long userId);

}
