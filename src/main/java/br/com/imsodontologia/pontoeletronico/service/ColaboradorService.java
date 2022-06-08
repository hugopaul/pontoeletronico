package br.com.imsodontologia.pontoeletronico.service;


import br.com.imsodontologia.pontoeletronico.model.PassMatches;
import br.com.imsodontologia.pontoeletronico.model.PerfilOfColaboradorDTO;
import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.TokenDTO;

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

    Colaborador getByToken(TokenDTO tokenDTO);

    Boolean matchPass(PassMatches passMatches);

    Colaborador setNewPass(UUID cdColaborador, Colaborador colaborador, String token);
}
