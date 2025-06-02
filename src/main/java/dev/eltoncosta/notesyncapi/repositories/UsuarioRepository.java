package dev.eltoncosta.notesyncapi.repositories;

import dev.eltoncosta.notesyncapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
