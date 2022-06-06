package br.com.imsodontologia.pontoeletronico.service;


import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.RequestLancamento;

import java.util.List;
import java.util.UUID;

public interface LancamentoService {
    Lancamento salvarLancamento(RequestLancamento requestLancamento);

    List<Lancamento> getAll();

    List<Lancamento> findByCdColaborador(UUID cdColaborador);

    Lancamento editarLancamento(UUID cdLancamento, Lancamento oldLancamento);

    Lancamento findByCdLancamento(UUID cdLancamento);

    Lancamento getLancamentoOfColaboradorByCdLancamento(UUID cdColaborador, UUID cdLancamento);
}
