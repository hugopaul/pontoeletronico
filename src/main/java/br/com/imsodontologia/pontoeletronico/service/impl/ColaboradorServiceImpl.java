package br.com.imsodontologia.pontoeletronico.service.impl;

import br.com.imsodontologia.pontoeletronico.model.PassMatches;
import br.com.imsodontologia.pontoeletronico.model.PerfilOfColaboradorDTO;
import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.TokenDTO;
import br.com.imsodontologia.pontoeletronico.repository.ColaboradorRepository;
import br.com.imsodontologia.pontoeletronico.security.JwtUtil;
import br.com.imsodontologia.pontoeletronico.service.ColaboradorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    @Autowired
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private JwtUtil jwtUtil;


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

        return this.colaboradorRepository.findAll().stream().filter(x -> !x.isDesativado()).collect(Collectors.toList());
    }

    @Override
    public Colaborador editarColaborador(UUID cdColaborador, Colaborador colaborador) {
        return this.colaboradorRepository.findById(colaborador.getCdColaborador()).map(pacienteDesatualizada -> {
            if (colaborador.getPassword().length() > 20){
                if (!colaborador.getPassword().equals(pacienteDesatualizada.getPassword())){
                    colaborador.setPassword(pacienteDesatualizada.getPassword());
                }
            }

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
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        }
        colaborador.get().setPerfis(perfilOfColaboradorDTO.getPerfisToAdd());
        log.info(String.valueOf(colaborador.get()));
        return this.colaboradorRepository.save(colaborador.get());
    }

    @Override
    public Colaborador getByToken(TokenDTO tokenDTO) {

        String username = jwtUtil.getUsername(tokenDTO.getToken());
        if (username.equals(tokenDTO.getType())) {
            Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
            if (colaborador.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
            }
            return colaborador.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario não bate com token enviado!");
    }

    @Override
    public Boolean matchPass(PassMatches passMatches) {

        return bCryptPasswordEncoder.matches(passMatches.getPassToMatch(), passMatches.getEncryptedPass());
    }

    @Override
    public Colaborador setNewPass(UUID cdColaborador, Colaborador colaborador, String token) {
        String username = jwtUtil.getUsername(token);
        if (username.equals(colaborador.getUsername())) {
            return this.colaboradorRepository.findById(colaborador.getCdColaborador()).map(pacienteDesatualizada -> {
                colaborador.setCdColaborador(cdColaborador);
                colaborador.setPassword(bCryptPasswordEncoder.encode(colaborador.getPassword()));
                return this.colaboradorRepository.save(colaborador);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Falha ao salvar senha do Colaborador"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario não bate com token enviado!");

    }

    @Override
    public void desativarColaborador(UUID cdColaborador) {

        Colaborador colaborador = this.colaboradorRepository.getById(cdColaborador);
        if (colaborador==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        }
        colaborador.setDesativado(true);
        this.colaboradorRepository.save(colaborador);
    }


}
