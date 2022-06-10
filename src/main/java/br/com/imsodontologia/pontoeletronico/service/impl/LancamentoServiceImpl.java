package br.com.imsodontologia.pontoeletronico.service.impl;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.RequestLancamento;
import br.com.imsodontologia.pontoeletronico.repository.ColaboradorRepository;
import br.com.imsodontologia.pontoeletronico.repository.LancamentoRepository;
import br.com.imsodontologia.pontoeletronico.security.JwtUtil;
import br.com.imsodontologia.pontoeletronico.service.LancamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private final LancamentoRepository repository;

    @Autowired
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public LancamentoServiceImpl(LancamentoRepository repository, ColaboradorRepository colaboradorRepository) {
        this.repository = repository;
        this.colaboradorRepository = colaboradorRepository;
    }


    @Override
    public Lancamento salvarLancamento(RequestLancamento requestLancamento, String token) {

        String username = jwtUtil.getUsername(token);
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        } else {
            Lancamento lancamento = new Lancamento();
            lancamento.setLatitude(requestLancamento.getGeolocationPosition().getCoords().getLatitude());
            lancamento.setLongitude(requestLancamento.getGeolocationPosition().getCoords().getLongitude());
            lancamento.setCdColaborador(colaborador.get());
            lancamento.setDhMarcacao(requestLancamento.getGeolocationPosition().getTimestamp());
            lancamento.setValid(true);
            log.info("Lançamento ----> " + lancamento);
            return this.repository.save(lancamento);
        }

    }

    @Override
    public List<Lancamento> getAll() {
        return this.repository.findAll();
    }

    @Override
    public List<Lancamento> findByCdColaborador(UUID cdColaborador) {
        return this.repository.findAllByCdColaborador(cdColaborador);
    }

    @Override
    public Lancamento editarLancamento(UUID cdLancamento, Lancamento newLancamento) {
        return repository.findById(newLancamento.getCdLancamento()).map(
                pacienteDesatualizada -> {
                    newLancamento.setCdLancamento(cdLancamento);
                    return repository.save(newLancamento);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));
    }

    @Override
    public Lancamento findByCdLancamento(UUID cdLancamento) {
        Lancamento lancamento = this.repository.findById(cdLancamento).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));


        return lancamento;
    }

    @Override
    public Lancamento getLancamentoOfColaboradorByCdLancamento(UUID cdColaborador, UUID cdLancamento) {
        Lancamento lancamento = this.repository.findById(cdLancamento).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));
        if (lancamento.getCdColaborador().getCdColaborador().equals(cdColaborador)) {
            return lancamento;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este Lançamento não pertence ao Colaborador");
    }
}
