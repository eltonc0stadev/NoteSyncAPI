package dev.eltoncosta.notesyncapi.DTOs;


import dev.eltoncosta.notesyncapi.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private Long id;
    private String titulo;
    private String conteudo;
    private User user;

}
