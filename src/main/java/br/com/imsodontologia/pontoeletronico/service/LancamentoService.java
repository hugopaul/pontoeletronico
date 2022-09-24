package br.com.imsodontologia.pontoeletronico.service;


import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.MeusLancamentosConcatenados;
import br.com.imsodontologia.pontoeletronico.model.RequestLancamento;

import java.util.List;
import java.util.UUID;

public interface LancamentoService {
    Lancamento salvarLancamento(RequestLancamento requestLancamento, String token);

    List<Lancamento> findByCdColaborador(String token);

    List<Object> getMeusLancamentosConcatenados(String authorization);

    List<Object> getAllLancamentos(String authorization);
}
