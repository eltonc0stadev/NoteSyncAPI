package dev.eltoncosta.notesyncapi.Services;

import dev.eltoncosta.notesyncapi.Models.Note;
import dev.eltoncosta.notesyncapi.Models.User;
import dev.eltoncosta.notesyncapi.Repositorys.NoteRepository;
import dev.eltoncosta.notesyncapi.Repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note criarNota(Note note) {
        if (note.getTitulo() == null || note.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }

        User user = userRepository.findById(note.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        note.setUser(user);

        return noteRepository.save(note);
    }

    public List<Note> listarNotasDoUsuario(Long idUsuario) {
        return noteRepository.findByUserId(idUsuario);
    }

    public Note buscarNota(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public void deletarNota(Long id) {
        noteRepository.deleteById(id);
    }

    public Note atualizarNota(Long id, Note note) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note noteExistente = optionalNote.get();
            noteExistente.setTitulo(note.getTitulo());
            noteExistente.setConteudo(note.getConteudo());
            noteExistente.setId(id);
            return noteRepository.save(noteExistente);
        }
        return null;
    }



}
