package br.com.imsodontologia.pontoeletronico.service.impl;

import br.com.imsodontologia.pontoeletronico.controller.PerfilOfColaboradorDTO;
import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Perfil;
import br.com.imsodontologia.pontoeletronico.repository.ColaboradorRepository;
import br.com.imsodontologia.pontoeletronico.service.ColaboradorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    @Autowired
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public Colaborador salvarColaborador(Colaborador colaborador) {
        colaborador.setPassword(bCryptPasswordEncoder.encode(colaborador.getPassword()));
        return this.colaboradorRepository.save(colaborador);
    }

    @Override
    public List<Colaborador> getAll() {
        return this.colaboradorRepository.findAll();
    }

    @Override
    public Colaborador editarColaborador(UUID cdColaborador, Colaborador colaborador) {
        return this.colaboradorRepository.findById(colaborador.getCdColaborador()).map(pacienteDesatualizada -> {
            colaborador.setCdColaborador(cdColaborador);
            return this.colaboradorRepository.save(colaborador);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Falha ao editar Colaborador"));
    }

    @Override
    public Colaborador getById(UUID cdColaborador) {
        return this.colaboradorRepository.findById(cdColaborador).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "não Encontrado"));
    }

    @Override
    public Optional<Colaborador> findById(UUID id) {
        return this.colaboradorRepository.findById(id);
    }

    @Override
    public Colaborador addRolesToUser(UUID cdColaborador, PerfilOfColaboradorDTO perfilOfColaboradorDTO) {
        Optional<Colaborador> colaborador = this.colaboradorRepository.findById(cdColaborador);
        if (colaborador.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        }
        colaborador.get().setPerfis(perfilOfColaboradorDTO.getPerfisToAdd());
        log.info(String.valueOf(colaborador.get()));
        return this.colaboradorRepository.save(colaborador.get());
    }

}
