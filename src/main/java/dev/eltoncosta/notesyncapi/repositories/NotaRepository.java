package dev.eltoncosta.notesyncapi.repositories;

import dev.eltoncosta.notesyncapi.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    Optional<List<Nota>> findByDonoId(Long donoId);
    Optional<List<Nota>> findByUsuariosCompartilhados_Id(Long usuarioId);
}
