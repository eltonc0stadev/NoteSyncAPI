package dev.eltoncosta.notesyncapi.controllers.request;

import java.util.List;

public record NotaRequest(String titulo,
                          String conteudo,
                          Boolean arquivada,
                          Boolean lixeira,
                          List<Long> usuariosCompartilhadosIds) {
}
