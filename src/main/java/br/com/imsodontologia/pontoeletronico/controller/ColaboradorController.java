package br.com.imsodontologia.pontoeletronico.controller;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Perfil;
import br.com.imsodontologia.pontoeletronico.service.ColaboradorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private  ColaboradorService colaboradorService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Colaborador gravarColaborador(@RequestBody Colaborador colaborador) {

        log.info("COLABORADOR RECEBIDO NO CONTROLLER ---> " + colaborador);

        return this.colaboradorService.salvarColaborador(colaborador);
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    @GetMapping("/{cdColaborador}")
    @ResponseStatus(HttpStatus.OK)
    public Colaborador getById(@PathVariable UUID cdColaborador) {
        return this.colaboradorService.getById(cdColaborador);
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Colaborador> getAll() {
        return this.colaboradorService.getAll();
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    @PutMapping("/{cdColaborador}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Colaborador editarColaborador(@PathVariable UUID cdColaborador, @RequestBody Colaborador colaborador) {
        return this.colaboradorService.editarColaborador(cdColaborador, colaborador);
    }

    @PreAuthorize("hasAnyRole('GERENTE')")
    @PostMapping("/perfis/{cdColaborador}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Colaborador addPerfil(@PathVariable UUID cdColaborador, @RequestBody PerfilOfColaboradorDTO perfilOfColaboradorDTO){
        return this.colaboradorService.addRolesToUser(cdColaborador, perfilOfColaboradorDTO);
    }

}
