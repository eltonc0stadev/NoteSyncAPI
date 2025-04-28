package dev.eltoncosta.notesyncapi.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"notes", "senha", "email"})
    private User user;

}
