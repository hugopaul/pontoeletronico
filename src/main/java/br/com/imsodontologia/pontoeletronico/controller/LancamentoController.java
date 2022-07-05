package br.com.imsodontologia.pontoeletronico.controller;

import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.MeusLancamentosConcatenados;
import br.com.imsodontologia.pontoeletronico.model.RequestLancamento;
import br.com.imsodontologia.pontoeletronico.service.LancamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    @Autowired
    public LancamentoController(LancamentoService service) {
        this.lancamentoService = service;
    }

    // SALVAR LANÇAMENTO ----> PAGE REGISTRAR PONTO
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Lancamento saveLancamento(@RequestBody RequestLancamento requestLancamento, HttpServletRequest request) {
        return this.lancamentoService.salvarLancamento(requestLancamento, request.getHeader("Authorization").substring(7));
    }



    // GET LANÇAMENTOS IN LAST 24 HOURS ----> PAGE REGISTRAR PONTO
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/colaborador")
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> getLancamentosByColaborador(HttpServletRequest request) {
        return this.lancamentoService.findByCdColaborador(request.getHeader("Authorization").substring(7));
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/getMeusLancamentosConcatenados")
    @ResponseStatus(HttpStatus.OK)
    public List<Object> getMeusLancamentosConcatenados(HttpServletRequest request){
        return this.lancamentoService.getMeusLancamentosConcatenados(request.getHeader("Authorization").substring(7));
    }




}
