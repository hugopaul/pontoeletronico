package br.com.imsodontologia.pontoeletronico.controller;

import br.com.imsodontologia.pontoeletronico.model.Perfil;
import lombok.Data;

import java.util.Set;

@Data
public class PerfilOfColaboradorDTO {

    private Set<Perfil> perfisToAdd;
}
