package dev.eltoncosta.notesyncapi.Mappers;

import dev.eltoncosta.notesyncapi.DTOs.NoteDTO;
import dev.eltoncosta.notesyncapi.Models.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public Note map(NoteDTO noteDTO) {
        Note note = new Note();
        note.setId(noteDTO.getId());
        note.setTitulo(noteDTO.getTitulo());
        note.setConteudo(noteDTO.getConteudo());
        note.setUser(noteDTO.getUser());
        return note;
    }

    public NoteDTO map(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setTitulo(note.getTitulo());
        noteDTO.setConteudo(note.getConteudo());
        noteDTO.setUser(note.getUser());
        return noteDTO;
    }

}
