package br.com.imsodontologia.pontoeletronico.service;


import br.com.imsodontologia.pontoeletronico.controller.PerfilOfColaboradorDTO;
import br.com.imsodontologia.pontoeletronico.model.Colaborador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColaboradorService {
    Colaborador salvarColaborador(Colaborador colaborador);

    List<Colaborador> getAll();

    Colaborador editarColaborador(UUID cdColaborador, Colaborador colaborador);

    Colaborador getById(UUID cdColaborador);

    Optional<Colaborador> findById(UUID id);

    Colaborador addRolesToUser(UUID cdColaborador, PerfilOfColaboradorDTO perfilOfColaboradorDTO);
}
