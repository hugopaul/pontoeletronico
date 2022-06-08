package br.com.imsodontologia.pontoeletronico.controller;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.PassMatches;
import br.com.imsodontologia.pontoeletronico.model.PerfilOfColaboradorDTO;
import br.com.imsodontologia.pontoeletronico.model.TokenDTO;
import br.com.imsodontologia.pontoeletronico.service.ColaboradorService;
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
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping(value = "/username")
    @ResponseStatus(HttpStatus.OK)
    public Colaborador getByUsername(@RequestBody TokenDTO tokenDTO) {
        return this.colaboradorService.getByToken(tokenDTO);
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

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/matchPass")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean matchPass(@RequestBody PassMatches passMatches){
        return this.colaboradorService.matchPass(passMatches);
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/setNewPass/{cdColaborador}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Colaborador setNewPass(@PathVariable UUID cdColaborador, @RequestBody Colaborador colaborador, HttpServletRequest request) {
        System.out.println("token --> " + request.getHeader("Authorization").substring(7));
        return this.colaboradorService.setNewPass(cdColaborador, colaborador, request.getHeader("Authorization").substring(7));
    }

}
