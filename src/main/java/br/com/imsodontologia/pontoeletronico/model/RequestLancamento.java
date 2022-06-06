package br.com.imsodontologia.pontoeletronico.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class RequestLancamento {

    private UUID cdColaborador;

    private GeolocationPosition geolocationPosition;


}
