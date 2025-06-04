package dev.eltoncosta.notesyncapi.services;

import dev.eltoncosta.notesyncapi.controllers.request.NotaResumoRequest;
import dev.eltoncosta.notesyncapi.controllers.request.NotaUpdateRequest;
import dev.eltoncosta.notesyncapi.controllers.request.UsuarioResumoRequest;
import dev.eltoncosta.notesyncapi.entities.Nota;
import dev.eltoncosta.notesyncapi.entities.Usuario;
import dev.eltoncosta.notesyncapi.exceptions.NotaNotFoundException;
import dev.eltoncosta.notesyncapi.exceptions.UsuarioNotFoundException;
import dev.eltoncosta.notesyncapi.repositories.NotaRepository;
import dev.eltoncosta.notesyncapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;

    public Nota buscarNotaPorId(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new NotaNotFoundException("Nota não encontrada"));
    }

    public Nota criarNota(Nota nota) {
        if (nota == null || nota.getTitulo() == null || nota.getConteudo() == null || nota.getDono() == null) {
            throw new NotaNotFoundException("Nota inválida");
        }
        if (!usuarioRepository.existsById(nota.getDono().getId())) {
            throw new UsuarioNotFoundException("Usuário não encontrado ou inexistente");
        }
        nota.setLixeira(false);
        nota.setArquivada(false);
        return notaRepository.save(nota);
    }

    public Nota deletarNota(Nota nota) {
        nota.setLixeira(true);
        return this.atualizarNota(nota);
    }

    public Nota restaurarNota(Nota nota) {
        nota.setLixeira(false);
        return this.atualizarNota(nota);
    }

    public Nota arquivarNota(Nota nota) {
        nota.setArquivada(true);
        return this.atualizarNota(nota);
    }

    public Nota desarquivarNota(Nota nota) {
        nota.setArquivada(false);
        return this.atualizarNota(nota);
    }

    public Nota atualizarNota(Nota nota) {
        if (nota == null || nota.getId() == null) {
            throw new NotaNotFoundException("Nota inválida");
        }
        Nota notaExistente = notaRepository.findById(nota.getId())
                .orElseThrow(() -> new NotaNotFoundException("Nota Invalida"));

        notaExistente.setTitulo(nota.getTitulo() != null ? nota.getTitulo() : notaExistente.getTitulo());
        notaExistente.setConteudo(nota.getConteudo() != null ? nota.getConteudo() : notaExistente.getConteudo());
        notaExistente.setArquivada(nota.getArquivada() != null ? nota.getArquivada() : notaExistente.getArquivada());
        notaExistente.setLixeira(nota.getLixeira() != null ? nota.getLixeira() : notaExistente.getLixeira());

        // Atualiza usuários compartilhados corretamente
        if (nota.getUsuariosCompartilhados() != null) {
            List<Long> usuariosCompartilhadosIds = nota.getUsuariosCompartilhados()
                    .stream()
                    .map(Usuario::getId)
                    .toList();
            List<Usuario> usuarios = usuariosCompartilhadosIds.isEmpty()
                    ? new ArrayList<>()
                    : usuarioRepository.findAllById(usuariosCompartilhadosIds);
            notaExistente.setUsuariosCompartilhados(usuarios);
        }

        return notaRepository.save(notaExistente);
    }

    public List<Nota> listarNotas(NotaResumoRequest notaRequest) {
        Long notaId = notaRequest.id();
        if (notaId == null) {
            throw new NotaNotFoundException("ID da nota não pode ser nulo");
        }
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new NotaNotFoundException("Nota não encontrada"));
        Long donoId = nota.getDono().getId();
        if (!usuarioRepository.existsById(donoId)) {
            throw new UsuarioNotFoundException("Usuário não encontrado ou inexistente");
        }
        List<Nota> notasDono = notaRepository.findByDonoId(donoId).orElse(List.of());
        List<Nota> notasCompartilhadas = notaRepository.findByUsuariosCompartilhados_Id(notaId).orElse(List.of());
        List<Nota> todasNotas = new ArrayList<>();
        todasNotas.addAll(notasDono);
        todasNotas.addAll(notasCompartilhadas);
        return todasNotas.stream()
                .filter(nota1 -> !nota1.getLixeira())
                .filter(nota1 -> !nota1.getArquivada())
                .distinct()
                .toList();
    }

    public Nota adicionarUsuarioCompartilhado(Nota nota, List<Long> usuariosCompartilhadosNovos) {
        Nota notaExistente = notaRepository.findById(nota.getId())
                .orElseThrow(() -> new NotaNotFoundException("Nota invalida"));
        List<Usuario> usuariosNovos = usuarioRepository.findAllById(usuariosCompartilhadosNovos);
        List<Usuario> usuariosCompartilhados = nota.getUsuariosCompartilhados();

        // Usa um Set para evitar duplicatas (por id)
        Set<Long> ids = new HashSet<>();
        List<Usuario> mesclados = new ArrayList<>();

        if (usuariosCompartilhados != null) {
            for (Usuario u : usuariosCompartilhados) {
                if (ids.add(u.getId())) {
                    mesclados.add(u);
                }
            }
        }
        for (Usuario u : usuariosNovos) {
            if (ids.add(u.getId())) {
                mesclados.add(u);
            }
        }

        notaExistente.setUsuariosCompartilhados(mesclados);
        return this.atualizarNota(notaExistente);

    }

    public Nota removerUsuariosCompartilhados(Nota nota, List<Long> usuarioIds) {
        Nota notaExistente = notaRepository.findById(nota.getId())
                .orElseThrow(() -> new NotaNotFoundException("Nota não encontrada"));

        List<Usuario> usuariosCompartilhados = notaExistente.getUsuariosCompartilhados();
        if (usuariosCompartilhados != null && usuarioIds != null) {
            usuariosCompartilhados.removeIf(usuario -> usuarioIds.contains(usuario.getId()));
            notaExistente.setUsuariosCompartilhados(usuariosCompartilhados);
        }

        return this.atualizarNota(notaExistente);
    }

    public List<Nota> listarNotasArquivadas(UsuarioResumoRequest usuarioRequest) {
        Long id = usuarioRequest.id();
        List<Nota> notasDono = notaRepository.findByDonoId(id).orElse(List.of());
        List<Nota> notasCompartilhadas = notaRepository.findByUsuariosCompartilhados_Id(id).orElse(List.of());
        List<Nota> todasNotas = new ArrayList<>();
        todasNotas.addAll(notasDono);
        todasNotas.addAll(notasCompartilhadas);
        return todasNotas.stream()
                .filter(Nota::getArquivada)
                .distinct()
                .toList();
    }

    public List<Nota> listarNotasLixeira(UsuarioResumoRequest usuarioRequest) {
        Long id = usuarioRequest.id();
        List<Nota> notasDono = notaRepository.findByDonoId(id).orElse(List.of());
        List<Nota> notasCompartilhadas = notaRepository.findByUsuariosCompartilhados_Id(id).orElse(List.of());
        List<Nota> todasNotas = new ArrayList<>();
        todasNotas.addAll(notasDono);
        todasNotas.addAll(notasCompartilhadas);
        return todasNotas.stream()
                .filter(Nota::getLixeira)
                .distinct()
                .toList();
    }

}
