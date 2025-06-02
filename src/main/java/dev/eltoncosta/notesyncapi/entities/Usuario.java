package dev.eltoncosta.notesyncapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Column(unique = true, nullable = false, name = "id_estudante")
    private String idEstudante;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Boolean desativado;

    // RELAÇÃO: Notas que ele é donoId
    @OneToMany(mappedBy = "dono")
    private List<Nota> notasProprias;

    // RELAÇÃO: Notas compartilhadas com ele
    @ManyToMany(mappedBy = "usuariosCompartilhados")
    private List<Nota> notasCompartilhadas;
}