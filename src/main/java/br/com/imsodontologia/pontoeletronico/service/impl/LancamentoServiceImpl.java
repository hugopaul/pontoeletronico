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
    private final LancamentoRepository lancamentoRepository;

    @Autowired
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public LancamentoServiceImpl(LancamentoRepository repository, ColaboradorRepository colaboradorRepository) {
        this.lancamentoRepository = repository;
        this.colaboradorRepository = colaboradorRepository;
    }


    @Override
    public Lancamento salvarLancamento(RequestLancamento requestLancamento, String token) {
        validarLancamento(requestLancamento);

        String username = jwtUtil.getUsername(token);
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        } else {
            Lancamento lancamento = new Lancamento();
            lancamento.setCdColaborador(colaborador.get());
            lancamento.setLatitude(requestLancamento.getLatitude());
            lancamento.setLongitude(requestLancamento.getLongitude());
            lancamento.setDhMarcacao(requestLancamento.getTimestamp());
            lancamento.setValid(true);
            return this.lancamentoRepository.save(lancamento);
        }

    }

    private void validarLancamento(RequestLancamento requestLancamento) {
        if (requestLancamento.getLatitude().isEmpty() || requestLancamento.getLatitude() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar Lançamento: Latitude is Empty");
        }
        if (requestLancamento.getLongitude().isEmpty() || requestLancamento.getLongitude() == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar Lançamento: Longitude is Empty");
        }
    }

    @Override
    public List<Lancamento> getAll() {
        return this.lancamentoRepository.findAll();
    }

    @Override
    public List<Lancamento> findByCdColaborador(String token) {
        String username = jwtUtil.getUsername(token);
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        } else {


            return this.lancamentoRepository.findAllByCdColaborador(colaborador.get().getCdColaborador());



        }

    }

    @Override
    public Lancamento editarLancamento(UUID cdLancamento, Lancamento newLancamento) {
        return lancamentoRepository.findById(newLancamento.getCdLancamento()).map(pacienteDesatualizada -> {
            newLancamento.setCdLancamento(cdLancamento);
            return lancamentoRepository.save(newLancamento);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));
    }

    @Override
    public Lancamento findByCdLancamento(UUID cdLancamento) {
        Lancamento lancamento = this.lancamentoRepository.findById(cdLancamento).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));


        return lancamento;
    }

    @Override
    public Lancamento getLancamentoOfColaboradorByCdLancamento(UUID cdColaborador, UUID cdLancamento) {
        Lancamento lancamento = this.lancamentoRepository.findById(cdLancamento).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lancamento não encontrado!"));
        if (lancamento.getCdColaborador().getCdColaborador().equals(cdColaborador)) {
            return lancamento;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este Lançamento não pertence ao Colaborador");
    }
}
