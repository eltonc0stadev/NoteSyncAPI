package dev.eltoncosta.notesyncapi.DTOs;

import dev.eltoncosta.notesyncapi.Models.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Note> notes = new ArrayList<>();

}
