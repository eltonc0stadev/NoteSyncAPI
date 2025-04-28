package dev.eltoncosta.notesyncapi.Services;

import dev.eltoncosta.notesyncapi.DTOs.NoteDTO;
import dev.eltoncosta.notesyncapi.Models.Note;
import dev.eltoncosta.notesyncapi.Models.User;
import dev.eltoncosta.notesyncapi.Repositorys.NoteRepository;
import dev.eltoncosta.notesyncapi.Repositorys.UserRepository;
import org.springframework.stereotype.Service;
import dev.eltoncosta.notesyncapi.Mappers.NoteMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteMapper = noteMapper;
    }

    public NoteDTO criarNota(NoteDTO note) {
        Note noteModel = noteMapper.map(note);
        if (note.getTitulo() == null || note.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }

        User user = userRepository.findById(note.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        note.setUser(user);

        noteModel = noteRepository.save(noteModel);

        return noteMapper.map(noteModel);
    }

    public List<NoteDTO> listarNotasDoUsuario(Long idUsuario) {
        List<Note> listaDeNotas = noteRepository.findByUserId(idUsuario);
        return listaDeNotas.stream().map(noteMapper::map).collect(Collectors.toList());
    }

    public NoteDTO buscarNota(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(noteMapper::map).orElse(null);
    }

    public void deletarNota(Long id) {
        noteRepository.deleteById(id);
    }

    public NoteDTO atualizarNota(Long id, NoteDTO note) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note noteExistente = optionalNote.get();
            noteExistente.setTitulo(note.getTitulo());
            noteExistente.setConteudo(note.getConteudo());
            noteExistente.setId(id);
            noteExistente = noteRepository.save(noteExistente);
            return noteMapper.map(noteExistente);
        }
        return null;
    }



}
