package br.com.imsodontologia.pontoeletronico.service.impl;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.MeusLancamentosConcatenados;
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

import java.util.List;
import java.util.Optional;


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
    public List<Object> getMeusLancamentosConcatenados(String authorization) {
        String username = jwtUtil.getUsername(authorization);
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        } else {
                 return this.lancamentoRepository.getAllLancamentosConcatenadosByColaborador(colaborador.get().getCdColaborador());
            }
        }

    @Override
    public List<Object> getAllLancamentos(String authorization) {
        String username = jwtUtil.getUsername(authorization);
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByUsername(username);
        if (colaborador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Colaborador não encontrado");
        } else {
            return this.lancamentoRepository.getAllLancamentosConcatenados();
        }
    }

}
