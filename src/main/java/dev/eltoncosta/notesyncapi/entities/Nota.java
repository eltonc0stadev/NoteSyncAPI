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
@Table(name = "nota")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String conteudo;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Boolean arquivada;
    private Boolean lixeira;

    // RELAÇÃO: Dono da nota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_dono_id", nullable = false)
    private Usuario dono;

    // RELAÇÃO: Usuários com acesso à nota
    @ManyToMany
    @JoinTable(
            name = "nota_usuarios_compartilhados",
            joinColumns = @JoinColumn(name = "nota_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosCompartilhados;
}