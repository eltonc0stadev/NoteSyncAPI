package dev.eltoncosta.notesyncapi.controllers.request;

import java.util.List;

public record NotaAddUserComp(Long idNota, List<Long> idUsersComp) {
}
