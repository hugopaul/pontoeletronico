package br.com.imsodontologia.pontoeletronico.controller;

import br.com.imsodontologia.pontoeletronico.model.Lancamento;
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
@RequestMapping("/lancamento")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    @Autowired
    public LancamentoController(LancamentoService service) {
        this.lancamentoService = service;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Lancamento saveLancamento(@RequestBody RequestLancamento requestLancamento, HttpServletRequest request) {
        return this.lancamentoService.salvarLancamento(requestLancamento, request.getHeader("Authorization").substring(7));
    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/colaborador")
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> getLancamentosByColaborador(HttpServletRequest request) {
        return this.lancamentoService.findByCdColaborador(request.getHeader("Authorization").substring(7));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/colaborador/{cdColaborador}/{cdLancamento}")
    @ResponseStatus(HttpStatus.OK)
    public Lancamento getLancamentoOfColaboradorByCdLancamento(@PathVariable UUID cdColaborador, @PathVariable UUID cdLancamento) {
        return this.lancamentoService.getLancamentoOfColaboradorByCdLancamento(cdColaborador, cdLancamento);
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> getAll() {
        return this.lancamentoService.getAll();
    }



    @PreAuthorize("hasAnyRole('GERENTE')")
    @GetMapping("/{cdLancamento}")
    @ResponseStatus(HttpStatus.OK)
    public Lancamento getLancamento(@PathVariable UUID cdLancamento) {
        return this.lancamentoService.findByCdLancamento(cdLancamento);
    }

    @PreAuthorize("hasAnyRole('PROPRIETARIO')")
    @PutMapping("/{cdLancamento}")
    @ResponseStatus(HttpStatus.OK)
    public Lancamento editarLancamento(@PathVariable UUID cdLancamento, @RequestBody Lancamento oldLancamento) {
        return this.lancamentoService.editarLancamento(cdLancamento, oldLancamento);
    }

}
